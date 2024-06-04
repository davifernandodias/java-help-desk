package com.systemupdate.beta.controllers.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * 
 * CONTROLLER QUE FAZ AUTENCICAÇÃO
 * COM A EMAIL E SENHA CRIPTOGRAFADA 
 * RESGATADA DO BANCO DE DADOS.
 * 
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(ModelMap model) {
        model.addAttribute("alerta", "erro");
        model.addAttribute("titulo", "Credenciais inválidas");
        model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
        model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativos.");

        return "login";
    }

}
