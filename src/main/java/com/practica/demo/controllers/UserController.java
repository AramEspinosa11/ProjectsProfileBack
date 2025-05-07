package com.practica.demo.controllers;

import com.practica.demo.models.DataModel;
import com.practica.demo.models.SessionModelRequest;
import com.practica.demo.models.SessionModelResponse;
import com.practica.demo.models.UserModel;
import com.practica.demo.services.UserService;
import com.practica.demo.services.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CryptoService cryptoService;

    @GetMapping(path = "/get-users")
    public ArrayList<UserModel> getUser(){
        return this.userService.getUsers();
    }

    @PostMapping(path = "/set-users")
    public UserModel saveUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }

    @GetMapping(path = "/get-users/{id}")
    public Optional<UserModel> getUserById(@PathVariable Long id){
        return this.userService.getById(id);
    }

    @PutMapping(path = "/update-user/{id}")
    public UserModel updateUserById(@RequestBody UserModel request, @PathVariable("id") Long id){
        return this.userService.updateById(request, id);
    }

    @DeleteMapping(path = "/delete-user/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok = this.userService.deleteUser(id);

        if(ok){
            return "User with id " + id + " deleted.";
        }else{
            return "Error";
        }
    }

    @PostMapping(path = "/start-session")
    public ResponseEntity<SessionModelResponse> startSession(@RequestBody DataModel request){
        return this.userService.startSession(request);
    }
}
