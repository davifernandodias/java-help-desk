package com.systemupdate.beta.controllers.chamados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.ChamadoRepository;
import com.systemupdate.beta.service.UsuarioService;
import com.systemupdate.beta.models.PerfilTipo;

@Controller
public class SearchController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChamadoRepository chamadoRepository;

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

        ModelAndView mv = new ModelAndView("ticket/searchchamado");

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