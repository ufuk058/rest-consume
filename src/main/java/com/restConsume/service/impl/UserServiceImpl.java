package com.restConsume.service.impl;

import com.restConsume.dto.UserDTO;
import com.restConsume.entity.User;
import com.restConsume.repository.UserRepository;
import com.restConsume.service.UserService;
import com.restConsume.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapperUtil.convert(user, new UserDTO())).collect(Collectors.toList());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {

        User user = userRepository.save(mapperUtil.convert(userDTO,new User()));
        return mapperUtil.convert(user,new UserDTO());
    }

    @Override
    public UserDTO findByUserName(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        return mapperUtil.convert(user,new UserDTO());
    }
}
