package com.jorge.tokenmoneyspringboot.currency;

import com.jorge.tokenmoneyspringboot.exception.CurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CurrencyController {

    @Autowired
    CurrencyServiceImpl currencyService ;


    @GetMapping("/tipo-cambio/token")
    public ResponseEntity<String> generateToken(
            @RequestParam(name = "from",defaultValue = "") String from,
            @RequestParam(name = "to",defaultValue = "") String to
    ){
        try {
            if (from == null || from.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'moneda origen' no puede ser vacío");
            }else  if (to == null || to.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'moneda destino' no puede ser vacío");
            } else {
                return ResponseEntity.ok().body(currencyService.generateToken(from, to));
            }
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
    @GetMapping("/tipo-cambio")
    public ResponseEntity<Object> getTipoCambio(
            @RequestHeader("Authorization") String authorization
    ){
        try {
            if(authorization.isBlank()){
                return ResponseEntity.badRequest().body("No tiene autorización");
            }
            return ResponseEntity.ok().body(currencyService.getTipoCambio(authorization));
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping(value = "/valor-cambio/token")
    public ResponseEntity<String> generateTokenFromToAmount(
            @RequestParam(name = "from",defaultValue = "") String from,
            @RequestParam(name = "to",defaultValue = "") String to,
            @RequestParam(name = "amount",defaultValue = "") String amount

    ){
        try {
            if (from == null || from.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'moneda origen' no puede ser vacío");
            }else  if (to == null || to.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'moneda destino' no puede ser vacío");
            }else  if (amount == null || amount.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'cantidad' no puede ser vacío");
            } else {
                int amountInteger = Integer.parseInt(amount);
                return ResponseEntity.ok().body(currencyService.generateTokenFromToAmount(from, to,amountInteger));
            }
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
    @GetMapping("/valor-cambio")
    public ResponseEntity<Object> getValorCambio(
            @RequestHeader("Authorization") String authorization
    ){
        try {
            if(authorization.isBlank()){
                return ResponseEntity.badRequest().body("No tiene autorización");
            }
            return ResponseEntity.ok().body(currencyService.getValorCambio(authorization));


        }catch (CurrencyException e){
            return   ResponseEntity.status(e.getCode()).body(e.getMessage());
        }

    }
}
