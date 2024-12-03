package com.restConsume.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllCurrencyResponse {

    private LocalDateTime dateTime;
    private boolean success;
    private String httpStatus;
    private String message;
    private List<CurrencyResponse> data;
}


