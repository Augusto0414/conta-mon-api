package com.augusto0414.conta_mon_api.repository;

import com.augusto0414.conta_mon_api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User,UUID> {
   Optional<User> findByEmail(String email);
}
