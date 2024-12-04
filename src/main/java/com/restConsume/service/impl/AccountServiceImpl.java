package com.restConsume.service.impl;

import com.restConsume.client.CurrencyApiClient;
import com.restConsume.dto.AccountDTO;
import com.restConsume.dto.UserDTO;
import com.restConsume.dto.response.AllCurrencyResponse;
import com.restConsume.dto.response.CurrencyResponse;
import com.restConsume.entity.Account;
import com.restConsume.entity.User;
import com.restConsume.repository.AccountRepository;
import com.restConsume.repository.UserRepository;
import com.restConsume.service.AccountService;
import com.restConsume.service.UserService;
import com.restConsume.util.MapperUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Value("${app.id}")
    private String appId;
    private final AccountRepository accountRepository;
    private final UserService userService;
    private final MapperUtil mapperUtil;
    private final CurrencyApiClient client;

    public AccountServiceImpl(AccountRepository accountRepository, UserService userService, MapperUtil mapperUtil, CurrencyApiClient client) {
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.mapperUtil = mapperUtil;
        this.client = client;
    }

    @Override
    public List<AccountDTO> findAllByUsername(String username) {
        return accountRepository.findAllByUser_Username(username).stream()
                .map(account -> {
                    AccountDTO accountDTO= mapperUtil.convert(account,new AccountDTO());
                    accountDTO.setUsername(username);
                    accountDTO.setOtherCurrencies(getAllCurrenciesByBalance(accountDTO.getBalance()));
                    return accountDTO;
                }).collect(Collectors.toList());
    }

    private Map<String, BigDecimal> getAllCurrenciesByBalance(BigDecimal balance){
        /// We need to send  request to consume API to get currency exchange rates
        AllCurrencyResponse allCurrencies= client.getAllCurrencies();
        List<CurrencyResponse> currencyResponseList=allCurrencies.getData();
        /// creating empty map to return
        Map<String, BigDecimal> otherCurrencies= new HashMap<>();

        /// calculate new balance for each currency code and assign balance values
        currencyResponseList.forEach(eachCurrency ->{
            BigDecimal currencyBalance = balance.multiply(eachCurrency.getGbpExchangeRate()).setScale(2, RoundingMode.HALF_UP);

            otherCurrencies.put(eachCurrency.getCurrencyCode(), currencyBalance);
        });

       /// return otherCurrencies
       return otherCurrencies;
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

    @Override
    public List<AccountDTO> findAllByUsernameAndCurrencyList(String username, List<String> currencyList) {
        return accountRepository.findAllByUser_Username(username).stream()
                .map(account -> {
                    AccountDTO accountDTO= mapperUtil.convert(account,new AccountDTO());
                    accountDTO.setUsername(username);
                    accountDTO.setOtherCurrencies(getListOfCurrenciesByBalanceSecure(accountDTO.getBalance(),currencyList));
                    return accountDTO;
                }).collect(Collectors.toList());
    }

    private Map<String, BigDecimal> getListOfCurrenciesByBalance(BigDecimal balance, List<String> currencyList){
        /// We need to send  request to consume API to get currency exchange rates
        AllCurrencyResponse allCurrencies= client.getListOfCurrencies(currencyList);
        List<CurrencyResponse> currencyResponseList=allCurrencies.getData();
        /// creating empty map to return
        Map<String, BigDecimal> otherCurrencies= new HashMap<>();

        /// calculate new balance for each currency code and assign balance values
        currencyResponseList.forEach(eachCurrency ->{
            BigDecimal currencyBalance = balance.multiply(eachCurrency.getGbpExchangeRate()).setScale(2, RoundingMode.HALF_UP);

            otherCurrencies.put(eachCurrency.getCurrencyCode(), currencyBalance);
        });

        /// return otherCurrencies
        return otherCurrencies;
    }

    private Map<String, BigDecimal> getListOfCurrenciesByBalanceSecure(BigDecimal balance, List<String> currencyList){
        /// We need to send  request to consume API to get currency exchange rates
        AllCurrencyResponse allCurrencies= client.getListOfCurrenciesSecure(currencyList,appId);
        List<CurrencyResponse> currencyResponseList=allCurrencies.getData();
        /// creating empty map to return
        Map<String, BigDecimal> otherCurrencies= new HashMap<>();

        /// calculate new balance for each currency code and assign balance values
        currencyResponseList.forEach(eachCurrency ->{
            BigDecimal currencyBalance = balance.multiply(eachCurrency.getGbpExchangeRate()).setScale(2, RoundingMode.HALF_UP);

            otherCurrencies.put(eachCurrency.getCurrencyCode(), currencyBalance);
        });

        /// return otherCurrencies
        return otherCurrencies;
    }

}
