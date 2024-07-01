package com.systemupdate.beta.controllers.colaboradores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.security.CustomUserDetails;
import com.systemupdate.beta.service.ColaboradorService;

@Controller
public class colaboradorConsultarController {

    @Autowired
    ColaboradorService colaboradorService;

    @GetMapping("/consultarColaborador")
    public String principal(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);
        if (isAuthenticated && auth.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            String role = userDetails.getRole();
            model.addAttribute("isAdmin", "ADMIN".equals(role));
        } else {
            model.addAttribute("isAdmin", false);
        }

        List<Colaborador> colaboradores = colaboradorService.buscarTodos();

        model.addAttribute("colaboradores", colaboradores);
        return "colaborador/consultarColaborador";
    }
}