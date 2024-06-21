package com.systemupdate.beta.controllers.chamados;

import java.time.LocalDateTime;

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
                               @RequestParam(required = false) String novoStatus,
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
        if (isAdmin || (chamado != null && chamado.getColaborador().equals(colaborador))) {
            mv.addObject("isAdmin", isAdmin);
            mv.addObject("chamado", chamado);

            if (chamado != null) {
                RespChamado respChamado = respChamadoRepository.findByChamado(chamado);
                boolean newRespoAdminExists = respChamado != null && respChamado.getRespoAdmin() != null && !respChamado.getRespoAdmin().trim().isEmpty();
                boolean isRespoAdminEmpty = respChamado == null || respChamado.getRespoAdmin() == null || respChamado.getRespoAdmin().trim().isEmpty();
                mv.addObject("newRespoAdminExists", newRespoAdminExists);
                mv.addObject("isRespoAdminEmpty", isRespoAdminEmpty);

                // Atualizar o status apenas se mudar de "aberto" para "andamento" ou de "andamento" para "finalizado"
                if ("aberto".equals(chamado.getStatus()) && "andamento".equals(novoStatus)) {
                    chamado.setStatus("andamento");
                    chamadoRepository.save(chamado);
                } else if ("andamento".equals(chamado.getStatus()) && "finalizado".equals(novoStatus)) {
                    chamado.setStatus("finalizado");
                    chamadoRepository.save(chamado);
                }

                // Salvar nova resposta administrativa apenas se newRespoAdmin não estiver vazio
                if (newRespoAdmin != null && !newRespoAdmin.trim().isEmpty()) {
                    RespChamado newRespChamado = new RespChamado();
                    newRespChamado.setRespoAdmin(newRespoAdmin);
                    newRespChamado.setChamado(chamado);
                    newRespChamado.setDataDeEnvio(LocalDateTime.now());
                    respChamadoRepository.save(newRespChamado);
                }
            }

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
