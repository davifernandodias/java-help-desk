package com.systemupdate.beta.controllers.colaboradores;


import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.Perfil;
import com.systemupdate.beta.models.PerfilTipo;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.ColaboradorRepository;
import com.systemupdate.beta.repository.EnderecoRepository;
import com.systemupdate.beta.repository.InformacaoAddRepository;
import com.systemupdate.beta.repository.PerfilRepository;
import com.systemupdate.beta.repository.UsuarioRepository;
import com.systemupdate.beta.security.CustomUserDetails;

@Controller
public class colaboradorCriarController {

    @Autowired
    ColaboradorRepository colaboradorRepository;
    @Autowired
    InformacaoAddRepository informacaoAddRepository;
    @Autowired
    EnderecoRepository enderecoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PerfilRepository perfilRepository;

    @GetMapping("/criarColaboradores")
    public String Home(ModelMap model) {
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
        return "colaborador/cadastroColaborador";
    }

    @PostMapping("/criarColaboradores/salvar")
    public String salvarUsuario(@ModelAttribute Usuario usuario,
                                @RequestParam String email,
                                @RequestParam String senha,
                                @RequestParam PerfilTipo perfilTipo,
                                @RequestParam String regiao,
                                @RequestParam String nome,
                                @RequestParam String cargo,
                                @RequestParam LocalDate dataNascimento,
                                @RequestParam Double salario,
                                Model model) {
    
        // Busca o perfil com base no tipo escolhido
        Perfil perfil = perfilRepository.findByDescricao(perfilTipo.getDescricao());
    
        // Verifica se o perfil foi encontrado
        if (perfil != null) {
            // Adiciona o perfil ao usuário
            usuario.addPerfil(perfil);
    
            // Define outras propriedades do usuário
            usuario.setAtivo(true);
            usuario.setEmail(email);
            usuario.setSenha(senha);
    
            // Verifica se o colaborador já existe
            Colaborador colaborador = usuario.getColaborador();
            if (colaborador == null) {
                colaborador = new Colaborador();
                colaborador.setUsuario(usuario); // Associa o colaborador ao usuário
                usuario.setColaborador(colaborador); // Associa o colaborador ao usuário
            }
    
            // Define as propriedades do colaborador
            colaborador.setNome(nome);
            colaborador.setCargo(cargo);
            colaborador.setDataNascimento(dataNascimento);
            colaborador.setSalario(salario);
            colaborador.setRegiao(regiao);
    
            // Salva o usuário e o colaborador no banco de dados
            usuarioRepository.save(usuario);
    
            // Redireciona para página adequada após salvar
            return "redirect:/criarColaboradores";
        } else {
            throw new IllegalArgumentException("Perfil não encontrado: " + perfilTipo.getDescricao());
        }
    }
    

}
