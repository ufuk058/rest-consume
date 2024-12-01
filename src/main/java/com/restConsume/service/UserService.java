package com.restConsume.service;

import com.restConsume.dto.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
    UserDTO create(UserDTO userDTO);
    UserDTO findByUserName(String username);
}
