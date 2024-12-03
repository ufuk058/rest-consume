package com.restConsume.service.impl;

import com.restConsume.dto.AccountDTO;
import com.restConsume.dto.UserDTO;
import com.restConsume.entity.Account;
import com.restConsume.entity.User;
import com.restConsume.repository.AccountRepository;
import com.restConsume.repository.UserRepository;
import com.restConsume.service.AccountService;
import com.restConsume.service.UserService;
import com.restConsume.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;
    private final MapperUtil mapperUtil;

    public AccountServiceImpl(AccountRepository accountRepository, UserService userService, MapperUtil mapperUtil) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<AccountDTO> findAllByUsername(String username) {
        return accountRepository.findAllByUser_Username(username).stream()
                .map(account -> {
                    AccountDTO accountDTO= mapperUtil.convert(account,new AccountDTO());
                    accountDTO.setUsername(username);
                    return accountDTO;
                }).collect(Collectors.toList());
    }

    private Long generateAccountNumber(){
        return (long)(Math.random()*100000000000000L);
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) {
        UserDTO user = userService.findByUserName(accountDTO.getUsername());

        Account accountToSave= mapperUtil.convert(accountDTO, new Account());
        accountToSave.setUser(mapperUtil.convert(user,new User()));
        accountToSave.setAccountNumber(generateAccountNumber());
        Account newAccount= accountRepository.save(accountToSave);
        AccountDTO accountToReturn= mapperUtil.convert(newAccount,new AccountDTO());
        accountToReturn.setUsername(user.getUsername());
        return accountToReturn;

    }

    @Override
    public List<AccountDTO> findAll() {
        List<Account> accountList= accountRepository.findAll();
        return accountList.stream().map(account -> mapperUtil.convert(account, new AccountDTO())).collect(Collectors.toList());
    }
}
