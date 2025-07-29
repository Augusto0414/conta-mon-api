package com.augusto0414.conta_mon_api.service.impl;

import com.augusto0414.conta_mon_api.dto.UserRequest;
import com.augusto0414.conta_mon_api.dto.UserResponse;
import com.augusto0414.conta_mon_api.models.User;
import com.augusto0414.conta_mon_api.repository.IUserRepository;
import com.augusto0414.conta_mon_api.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserServiceImpl (IUserRepository repository){
        this.repository = repository;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if(request == null) return null;
        if(repository.findByEmail(request.getEmail()).isPresent()) return null;
        if(request.getPassword().isEmpty()) return null;
        String hashPaswword = hashPassword(request.getPassword());
        User userEntity = new User();
        userEntity.setUserName(request.getUserName());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(hashPaswword);
        User saved = repository.save(userEntity);
        return toSaveUserResponse(saved);
    }

    private String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    private boolean matchPassword(String plainPassword,String password){
        return passwordEncoder.matches(plainPassword,password);
    }

    private UserResponse toSaveUserResponse(User save){
        return UserResponse.builder()
                .id(save.getId())
                .userName(save.getUserName())
                .email(save.getEmail())
                .build();
    }
}
