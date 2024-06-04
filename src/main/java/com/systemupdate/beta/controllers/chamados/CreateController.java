package com.systemupdate.beta.controllers.chamados;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.ChamadoRepository;
import com.systemupdate.beta.service.UsuarioService;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class CreateController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChamadoRepository chamadoRepository;

    
    @GetMapping({ "/chamado" })
    public String principal(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("chamado", new Chamado());
        return "ticket/openchamado";
    }

    @PostMapping("/chamado/salvar")
    public String salvarChamado(@ModelAttribute("chamado") Chamado chamado, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        Usuario usuario = usuarioService.findByEmail(userEmail);

        Colaborador colaborador = usuario.getColaborador();
        chamado.setColaborador(colaborador);
        chamado.setDataCriacao(LocalDateTime.now());
        chamadoRepository.save(chamado);

        model.addAttribute("sucesso", "Chamado salvo com sucesso!");

        return "ticket/openchamado";
    }


}
