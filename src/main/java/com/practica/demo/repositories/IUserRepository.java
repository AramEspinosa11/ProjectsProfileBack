package com.practica.demo.repositories;

import com.practica.demo.models.UserModel;
import com.practica.demo.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByEmailAndPassword(String email, String password);
}
