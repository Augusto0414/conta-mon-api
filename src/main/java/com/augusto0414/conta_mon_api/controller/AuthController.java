package com.augusto0414.conta_mon_api.controller;

import com.augusto0414.conta_mon_api.dto.AuthRequest;
import com.augusto0414.conta_mon_api.dto.DefaultResponse;
import com.augusto0414.conta_mon_api.dto.UserRequest;
import com.augusto0414.conta_mon_api.dto.UserResponse;
import com.augusto0414.conta_mon_api.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IUserService service;

    public AuthController(IUserService service) {
        this.service = service;
    }

    @PostMapping(value = "/created")
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

    @PostMapping(value = "/login")
    public DefaultResponse<Map<String, ?>> login(@RequestBody AuthRequest request){
        Map<String, ?> response = service.login(request);
        if(response == null){
            return DefaultResponse.<Map<String, ?>>builder()
                    .error(true)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("No hay datos de envio")
                    .body(null)
                    .build();
        }

        return DefaultResponse.<Map<String, ?>>builder()
                .error(false)
                .code(HttpStatus.OK.value())
                .message("Login exitoso")
                .body(response)
                .build();

    }
}
