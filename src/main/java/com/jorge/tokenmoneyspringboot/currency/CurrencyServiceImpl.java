package com.jorge.tokenmoneyspringboot.currency;

import com.jorge.tokenmoneyspringboot.configuration.JwtProvider;
import com.jorge.tokenmoneyspringboot.exception.CurrencyException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyServiceImpl {
    @Autowired
    JwtProvider jwtProvider;
    public String generateToken(String fromCurrency,String toCurrency ){
        return jwtProvider.generateToken(fromCurrency,toCurrency);
    }

    public Exchangerate getTipoCambio(String authorization) throws Exception {
        Exchangerate exchangerate = null;
        try{
            Claims claims = jwtProvider.validatingAuthorization(authorization);
            if(claims.containsKey("from") && claims.containsKey("to") ){
                String from = claims.get("from").toString();
                String to = claims.get("to").toString();
                String url = "https://api.exchangerate.host/convert?from=".concat(from).concat("&to=").concat(to);

                RestTemplate restTemplate = new RestTemplate();
                exchangerate  = restTemplate.getForObject(url,Exchangerate.class);

        }

       }catch (Exception e){
            throw new Exception("Hubo un error: "+e.getMessage());
        }
        return exchangerate;
    }
    public double getValorCambio(String authorization)  {
        Exchangerate exchangerate = null;
        Double result = null;
        try{
            Claims claims = jwtProvider.validatingAuthorization(authorization);
            if(claims.containsKey("from") && claims.containsKey("to") && claims.containsKey("amount") ){
                String from = claims.get("from").toString();
                String to = claims.get("to").toString();
                int amount = (Integer) claims.get("amount");
                String url = "https://api.exchangerate.host/convert?from=".concat(from).concat("&to=").concat(to);

                RestTemplate restTemplate = new RestTemplate();
                exchangerate  = restTemplate.getForObject(url,Exchangerate.class);
                if(exchangerate != null && exchangerate.getInfo() != null){
                    Map<String,Double> map = (HashMap<String,Double>) exchangerate.getInfo();
                    double rate = Double.parseDouble(map.get("rate").toString());
                    result  = amount * rate;
                }
            }else{
                throw new CurrencyException(400,"El token debe tener los claims 'from','to' y 'amount' para que pueda ser válido");
            }
            return result;

        }catch (CurrencyException e){
            throw new CurrencyException(400,e.getMessage());
        }

    }

    public String generateTokenFromToAmount(String from, String to, int amount) {
        return jwtProvider.generateTokenFromToAmount(from,to,amount);
    }
}
