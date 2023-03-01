package com.jorge.tokenmoneyspringboot.currency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tipo-cambio")
public class CurrencyController {

    @Autowired
    CurrencyServiceImpl currencyService ;

    @GetMapping("")
        public ResponseEntity<String> test(){
          return ResponseEntity.ok().body("test");
        }


    @GetMapping("/token")
    public ResponseEntity<String> tipoCambio(
            @RequestParam(name = "from",defaultValue = "") String from,
            @RequestParam(name = "to",defaultValue = "") String to
    ){
        try {
            if (from == null || from.isBlank()) {
                return ResponseEntity.badRequest().body("El campo 'moneda origen' no puede ser vacío");
            } else {
                return ResponseEntity.ok().body(currencyService.generateToken(from, to));
            }
        }catch (Exception e){
            return ResponseEntity.ok().body(e.getMessage());
        }
    }
}
