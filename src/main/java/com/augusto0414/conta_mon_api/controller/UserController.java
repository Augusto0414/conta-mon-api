package com.augusto0414.conta_mon_api.controller;

import com.augusto0414.conta_mon_api.dto.DefaultResponse;
import com.augusto0414.conta_mon_api.dto.UserRequest;
import com.augusto0414.conta_mon_api.dto.UserResponse;
import com.augusto0414.conta_mon_api.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService service;

    public UserController(IUserService service) {
        this.service = service;
    }

    @GetMapping
    public String gellAll(){
        return "hello";
    }

    @PostMapping
    public DefaultResponse<UserResponse> createUser(@RequestBody UserRequest request){
        UserResponse response = service.createUser(request);
        if (response == null) {
            return DefaultResponse.<UserResponse>builder()
                    .error(true)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("No se pudo crear el usuario")
                    .body(null)
                    .build();
        }

        return DefaultResponse.<UserResponse>builder()
                .error(false)
                .code(HttpStatus.CREATED.value())
                .message("Usuario creado exitosamente")
                .body(response)
                .build();
    }
}
