package com.restConsume.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ResponseWrapper {
    private boolean success;
    private String message;
    private Integer code;
    private Object data;


//    public ResponseWrapper(String message, Object data){
//        this.message=message;
//        this.data=data;
//        this.code= HttpStatus.CREATED.value();
//        this.success=true;
//    }
}
