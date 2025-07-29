package com.augusto0414.conta_mon_api.service;

import com.augusto0414.conta_mon_api.dto.UserRequest;
import com.augusto0414.conta_mon_api.dto.UserResponse;

public interface IUserService {
     UserResponse createUser(UserRequest request);
}
