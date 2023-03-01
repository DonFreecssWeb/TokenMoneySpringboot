package com.jorge.tokenmoneyspringboot.exception;

import lombok.Data;

@Data
public class CurrencyException extends RuntimeException{

    private final int code;
    private final String message;

    public CurrencyException(int codeResponse,String message){
        this.code = codeResponse;
        this.message= message;
    }
}
