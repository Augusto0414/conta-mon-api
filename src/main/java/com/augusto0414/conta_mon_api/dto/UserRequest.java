package com.augusto0414.conta_mon_api.dto;

import lombok.Data;


@Data
public class UserRequest {
    private String userName;
    private String email;
    private String password;
}
