package com.systemupdate.beta.controllers.chamados;

import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.repository.ChamadoRepository;
import com.systemupdate.beta.service.UsuarioService;

@Controller
public class CreateController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ChamadoRepository chamadoRepository;

    @GetMapping("/chamado")
    public String principal(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("chamado", new Chamado());
        return "ticket/cadastrarChamado";
    }

    @PostMapping("/chamado/salvar")
    public String salvarChamado(@ModelAttribute Chamado chamado) {
        try {
            // Obter o colaborador atualmente logado
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = auth.getName();
            Colaborador colaborador = usuarioService.findByEmail(userEmail).getColaborador();
            
            // Configurar o colaborador no objeto Chamado
            chamado.setColaborador(colaborador);
            chamado.setDataAtualizacao(LocalDateTime.now());
            chamado.setDataAbertura(LocalDateTime.now());
            chamado.setStatus("aberto");
    
            // Salvar o chamado no banco de dados através do repositório
            chamadoRepository.save(chamado);
    
            // Redirecionar para a página de listagem de chamados ou outra ação desejada
            return "redirect:/chamado";
        } catch (Exception e) {
            // Tratar exceção (logar, relançar, etc.)
            // Redirecionar para uma página de erro ou mostrar uma mensagem relevante
            return "redirect:/error";
        }
    }
    
    
}
