package com.restConsume.controller;

import com.restConsume.dto.ResponseWrapper;
import com.restConsume.dto.UserDTO;
import com.restConsume.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllUsers(){

        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true).message("Users are successfully retrieved")
                .code(HttpStatus.OK.value()).
                data(userService.findAll()).build());
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .message("User:"+username+" is retrieved.")
                .code(HttpStatus.OK.value())
                .data(userService.findByUserName(username))
                .build());
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createNewUser(@RequestBody UserDTO user){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.CREATED.value())
                        .message("User: "+user.getUsername()+" created")
                        .data(userService.create(user)).build());
    }
}
