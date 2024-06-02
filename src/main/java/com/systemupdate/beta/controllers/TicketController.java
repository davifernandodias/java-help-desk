package com.systemupdate.beta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.service.ChamadoService;


@Controller
public class TicketController {

    @Autowired
    private ChamadoService chamadoService;

    @GetMapping("abrirticket")
    public String viewTicket(ModelMap model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        /*
         * MODAL DE AUTENTICAÇÃO E FILTRAGEM DE PERFIL DE ADMIN OU COLABORADOR.
         * 
         */

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);
        return "ticket/openchamado";
    }
    public String salvar(Chamado chamado, RedirectAttributes attr){
        chamadoService.salvar(chamado);
        attr.addFlashAttribute("sucesso","Operação realizada com sucesso");
        return "ticket/openchamdo";
    }
    
}
