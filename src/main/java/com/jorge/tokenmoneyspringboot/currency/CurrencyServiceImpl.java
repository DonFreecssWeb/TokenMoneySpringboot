package com.jorge.tokenmoneyspringboot.currency;

import com.jorge.tokenmoneyspringboot.configuration.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyServiceImpl {
    @Autowired
    JwtProvider jwtProvider;
    public String generateToken(String fromCurrency,String toCurrency ){
        return jwtProvider.generateToken(fromCurrency,toCurrency);
    }
}
