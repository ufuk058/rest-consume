package com.restConsume.client;

import com.restConsume.dto.response.AllCurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url ="http://www.cydeodev.com",name = "currency-service")
public interface CurrencyApiClient {

    @GetMapping("/api/v1/currency/all")
    AllCurrencyResponse getAllCurrencies();

    @GetMapping("/api/v1/currency/list")
    AllCurrencyResponse getListOfCurrencies(@RequestParam("cureencies")List<String> currencies);

    //http://www.cydeodev.com/api/v1/currency/secure/list?currencies=EUR&currencies=GBP
    // app-id = Cydeo-123   --> add this to header as an authentication

    @GetMapping("/api/v1/currency/secure/list")
    AllCurrencyResponse getListOfCurrenciesSecure(@RequestParam("currencies")List<String> currencies,
                                                  @RequestHeader("app-id") String appId);
}
