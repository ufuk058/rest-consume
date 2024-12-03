package com.restConsume.client;

import com.restConsume.dto.response.AllCurrencyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url ="http://www.cydeodev.com",name = "currency-service")
public interface CurrencyApiClient {

    @GetMapping("/api/v1/currency/all")
    AllCurrencyResponse getAllCurrencies();
}
