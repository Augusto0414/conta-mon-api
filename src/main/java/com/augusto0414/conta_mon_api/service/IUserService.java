package com.augusto0414.conta_mon_api.service;

import com.augusto0414.conta_mon_api.dto.AuthRequest;
import com.augusto0414.conta_mon_api.dto.UserRequest;
import com.augusto0414.conta_mon_api.dto.UserResponse;

import java.util.Map;

public interface IUserService {
     UserResponse createUser(UserRequest request);
     Map<String, ?> login (AuthRequest request);
}
