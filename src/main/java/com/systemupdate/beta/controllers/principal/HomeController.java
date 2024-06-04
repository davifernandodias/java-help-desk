package com.systemupdate.beta.controllers.principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class HomeController {

    @GetMapping({ "/", "/home" })
    public String view(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        /*
         * MODAL DE AUTENTICAÇÃO E FILTRAGEM DE PERFIL DE ADMIN OU COLABORADOR.
         * 
         */
        model.addAttribute("isAuthenticated", isAuthenticated);
        

        return "infopage/home";
    }



}
