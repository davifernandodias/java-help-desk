package com.systemupdate.beta.controllers.chamados;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.systemupdate.beta.models.Chamado;
import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.ChamadoRepository;
import com.systemupdate.beta.repository.RespChamadoRepository;
import com.systemupdate.beta.service.UsuarioService;

import com.systemupdate.beta.models.PerfilTipo;
import com.systemupdate.beta.models.RespChamado;

@Controller
public class SearchController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private RespChamadoRepository respChamadoRepository;

    @RequestMapping("/consultar")
    public ModelAndView searchChamadosView(ModelMap model, String status) {
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

        mv.addObject("isAdmin", isAdmin);

        if (isAdmin) {
            Iterable<Chamado> chamados = chamadoRepository.findAll();
            mv.addObject("chamados", chamados);

        } else {
            Iterable<Chamado> chamados = chamadoRepository.findByColaborador(colaborador);
            mv.addObject("chamados", chamados);
        }

        return mv;
    }

    @RequestMapping("/detalhes/{id}")
    public ModelAndView consultaPorID(@PathVariable Long id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);

        Usuario usuario = usuarioService.findByEmail(userEmail);
        Chamado chamado = chamadoRepository.findById(id).orElse(null);
        Colaborador colaborador = usuario.getColaborador();

        boolean isAdmin = usuario.getPerfis().stream()
                .anyMatch(perfil -> perfil.getDescricao().equals(PerfilTipo.ADMIN.getDescricao()));

        ModelAndView mv = new ModelAndView("ticket/detalhesChamados");
        if (isAdmin || (chamado != null && chamado.getColaborador().equals(usuario.getColaborador()))) {
            mv.addObject("isAdmin", isAdmin);
            mv.addObject("chamado", chamado);
            if (isAdmin) {
                Iterable<Chamado> chamados = chamadoRepository.findAll();
                mv.addObject("chamados", chamados);
            } else {
                Iterable<Chamado> chamados = chamadoRepository.findByColaborador(colaborador);
                mv.addObject("chamados", chamados);
            }
        } else {
            mv.addObject("error", "Você não tem permissão para acessar este chamado.");
        }
        return mv;
    }

    @RequestMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id, ModelMap model,
            @RequestParam String novoStatus,
            @RequestParam(required = false) String newRespoAdmin) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);

        Usuario usuario = usuarioService.findByEmail(userEmail);
        Chamado chamado = chamadoRepository.findById(id).orElse(null);
        Colaborador colaborador = usuario.getColaborador();

        boolean isAdmin = usuario.getPerfis().stream()
                .anyMatch(perfil -> perfil.getDescricao().equals(PerfilTipo.ADMIN.getDescricao()));

        ModelAndView mv = new ModelAndView("ticket/detalhesChamados");
        if (isAdmin || (chamado != null && chamado.getColaborador().equals(usuario.getColaborador()))) {
            mv.addObject("isAdmin", isAdmin);
            mv.addObject("chamado", chamado);

            chamado.setStatus(novoStatus);

            // Inicializa respChamado e salva a resposta administrativa
            RespChamado respChamado = new RespChamado();
            respChamado.setRespoAdmin(newRespoAdmin);
            respChamadoRepository.save(respChamado);

            chamadoRepository.save(chamado);

            if (isAdmin) {
                Iterable<Chamado> chamados = chamadoRepository.findAll();
                mv.addObject("chamados", chamados);
            } else {
                Iterable<Chamado> chamados = chamadoRepository.findByColaborador(colaborador);
                mv.addObject("chamados", chamados);
            }
        } else {
            mv.addObject("error", "Você não tem permissão para acessar este chamado.");
        }
        return mv;
    }
            }

