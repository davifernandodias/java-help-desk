package com.systemupdate.beta.controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.ChamadoRepository;
import com.systemupdate.beta.service.UsuarioService;
import com.systemupdate.beta.models.PerfilTipo;

import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class TicketController {

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

    @RequestMapping("/consultar")
    public ModelAndView searchChamadosView(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);

        Usuario usuario = usuarioService.findByEmail(userEmail);
        Colaborador colaborador = usuario.getColaborador();

        boolean isAdmin = usuario.getPerfis().stream()
                                 .anyMatch(perfil -> perfil.getDescricao().equals(PerfilTipo.ADMIN.getDescricao()));

        ModelAndView mv = new ModelAndView("searchchamado");

        if (isAdmin) {
            Iterable<Chamado> chamados = chamadoRepository.findAll();
            mv.addObject("chamados", chamados);
        } else {
            Iterable<Chamado> chamados = chamadoRepository.findByColaborador(colaborador);
            mv.addObject("chamados", chamados);
        }

        return mv;
    }
}
