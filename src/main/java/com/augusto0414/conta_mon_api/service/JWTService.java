package com.augusto0414.conta_mon_api.service;

import java.util.Map;

public interface JWTService {
    String generateToken(String subject, Map<String, Object> claims);
    Boolean isTokenValid(String token);
    Long getExpirationTime();
    String extractSubject(String token);
}
