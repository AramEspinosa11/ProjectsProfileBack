package com.practica.demo.repositories;

import com.practica.demo.models.UserModel;
import com.practica.demo.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserSessionRepository extends JpaRepository<UserSession, Long> {
    /*Optional<UserSession> findExistingSession(Integer user_id, String usertoken, Integer status);*/
}
