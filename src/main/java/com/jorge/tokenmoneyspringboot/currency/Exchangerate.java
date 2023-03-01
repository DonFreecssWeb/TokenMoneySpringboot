package com.jorge.tokenmoneyspringboot.currency;

import lombok.Data;

@Data
public class Exchangerate {

    private boolean success;
    private Object query;
    private Object info;
    private boolean historical;
    private String date;
    private double result;
}
