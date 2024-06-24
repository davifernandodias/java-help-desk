package com.systemupdate.beta.controllers.perfil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.InformacaoAdicionaisColaborador;
import com.systemupdate.beta.models.PerfilTipo;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.service.UsuarioService;



@Controller
public class PerfilColaboradorController {

    @Autowired
    private UsuarioService usuarioService;

    
 



    @GetMapping("/profile")
    public String principal(ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);

        // Encontra o usuário pelo email
        Usuario usuario = usuarioService.findByEmail(userEmail);
        if (usuario == null) {
            // Tratar caso usuário não seja encontrado
            return "redirect:/login"; // ou outro tratamento adequado
        }

        // Obtém o colaborador associado ao usuário
        Colaborador colaborador = usuario.getColaborador();
        if (colaborador == null) {
            // Tratar caso o colaborador não seja encontrado
            return "redirect:/login"; // ou outro tratamento adequado
        }

        // Obtém as informações adicionais do colaborador
        InformacaoAdicionaisColaborador informacaoAdicionaisColaborador = colaborador.getInformacaoAdicionais();
        if (informacaoAdicionaisColaborador == null) {
            // Tratar caso as informações adicionais não sejam encontradas
            informacaoAdicionaisColaborador = new InformacaoAdicionaisColaborador(); // ou outro tratamento adequado
        }

        // Verifica se o usuário é administrador
        boolean isAdmin = usuario.getPerfis().stream()
                .anyMatch(perfil -> perfil.getDescricao().equals(PerfilTipo.ADMIN.getDescricao()));

        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("colaborador", colaborador);
        model.addAttribute("informacao", informacaoAdicionaisColaborador);

        return "index/configperfil"; // Retorna o nome do template a ser renderizado
    }
}


    

