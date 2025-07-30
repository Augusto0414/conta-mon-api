package com.augusto0414.conta_mon_api.service.impl;

import com.augusto0414.conta_mon_api.dto.AuthRequest;
import com.augusto0414.conta_mon_api.dto.UserRequest;
import com.augusto0414.conta_mon_api.dto.UserResponse;
import com.augusto0414.conta_mon_api.models.User;
import com.augusto0414.conta_mon_api.repository.IUserRepository;
import com.augusto0414.conta_mon_api.service.IUserService;
import com.augusto0414.conta_mon_api.service.JWTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository repository;
    private final JWTService jwtService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    Map<String, Object> claims = new HashMap<>();

    public UserServiceImpl (IUserRepository repository, JWTService jwtService){
        this.repository = repository;
        this.jwtService = jwtService;
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

    @Override
    public Map<String, ?> login(AuthRequest request) {
        if (request == null) throw new IllegalArgumentException("Request cannot be null");

        Optional<User> userOpt = repository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) throw new UsernameNotFoundException("User not found");

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }

        claims.put("email", user.getEmail());
        claims.put("userName", user.getUserName());

        String token = jwtService.generateToken(user.getEmail(), claims);

        UserResponse response = toSaveUserResponse(user);

        return Map.of(
                "response", response,
                "token", token
        );
    }


    private String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

    private UserResponse toSaveUserResponse(User save){
        return UserResponse.builder()
                .id(save.getId())
                .userName(save.getUserName())
                .email(save.getEmail())
                .build();
    }
}
