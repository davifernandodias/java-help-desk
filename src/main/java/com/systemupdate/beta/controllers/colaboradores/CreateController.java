package com.systemupdate.beta.controllers.colaboradores;

import org.springframework.web.bind.annotation.GetMapping;

public class CreateController {
    
    @GetMapping("/castro")
    public String HomeColaboradores(){
        return "olá";
    }
}
