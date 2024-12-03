package com.restConsume.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponse {

    private String currencyName;
    private String currencyCode;
    private String currencySymbol;
    private BigDecimal gbpExchangeRate;
}
