package com.restConsume.service;

import com.restConsume.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    List<AccountDTO> findAllByUsername(String username);
    AccountDTO create(AccountDTO accountDTO);

    List<AccountDTO> findAll();
}
