package com.restConsume.controller;

import com.restConsume.dto.AccountDTO;
import com.restConsume.dto.UserDTO;
import com.restConsume.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<AccountDTO> getAllAccounts(){
        return accountService.findAll();
    }

    @GetMapping("/all/{username}")
    public List<AccountDTO> getUserAllAccount(@PathVariable String username){

        return accountService.findAllByUsername(username);
    }
}
