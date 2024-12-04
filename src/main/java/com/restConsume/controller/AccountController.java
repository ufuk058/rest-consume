package com.restConsume.controller;

import com.restConsume.dto.AccountDTO;
import com.restConsume.dto.ResponseWrapper;
import com.restConsume.dto.UserDTO;
import com.restConsume.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper> getAllAccounts(){

        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .message("All accounts retrieved successfully")
                .code(HttpStatus.OK.value())
                .data(accountService.findAll()).build());
    }



    @GetMapping("/all/{username}")
    public ResponseEntity<ResponseWrapper> getUserAllAccount(@PathVariable String username){

        return ResponseEntity.ok(ResponseWrapper.builder()
                        .success(true)
                        .message("User: "+username+" retrieved successfully")
                        .code(HttpStatus.OK.value())
                        .data(accountService.findAllByUsername(username)).build());
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper> createAccount(@RequestBody AccountDTO accountDTO ){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseWrapper.builder()
                        .success(true)
                        .code(HttpStatus.CREATED.value())
                        .message("Account successfully created")
                        .data(accountService.create(accountDTO)).build());
    }

    //  /api/v1/account
    //  /all/currencies/{username} ?currencies=EUR, HUF, JPY
    @GetMapping("all/currencies/{username}")
    public ResponseEntity<ResponseWrapper> getAllAccountsByUsernameAndCurrencies(@PathVariable("username") String username,
                                                                                 @RequestParam(name="currencyList") List<String> currencyList){

        return ResponseEntity.ok(ResponseWrapper.builder()
                .success(true)
                .message("Account is successfully retrieved with the required currencies")
                .code(HttpStatus.OK.value())
                .data(accountService.findAllByUsernameAndCurrencyList(username,currencyList)).build());

    }
}
