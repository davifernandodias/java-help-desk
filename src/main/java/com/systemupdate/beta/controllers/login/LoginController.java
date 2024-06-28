package com.systemupdate.beta.controllers.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.service.UsuarioService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(ModelMap model, HttpServletRequest resp) {
        HttpSession session = resp.getSession();
        String lastExpection = String.valueOf( session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION"));
        if(lastExpection.contains(SessionAuthenticationException.class.getName())){
            model.addAttribute("alerta", "erro");
            model.addAttribute("titulo", "Acesso recusado");
            model.addAttribute("texto", "Você já está logado em outro dispositivo.");
            model.addAttribute("subtexto", "Faça logout ou espere sua sessão expirar.");
            return "login";
        }
        model.addAttribute("alerta", "erro");
        model.addAttribute("titulo", "Credenciais inválidas");
        model.addAttribute("texto", "Login ou senha incorretos, tente novamente.");
        model.addAttribute("subtexto", "Acesso permitido apenas para cadastros já ativos.");

        return "login";
    }

    @GetMapping("/login-redefinir-senha")
    public String pedidoRedefinirSenha() {
        return "pedido-recuperar-senha";
    }

    @GetMapping("/login-recuperar-senha")
    public String redefinirSenha(String email, ModelMap model) throws MessagingException {
        usuarioService.pedidoRefinicaoDeSenha(email);
        model.addAttribute("sucesso", "Em instante você receberá um e-mail para " +
                "prosseguir com a redefinição de sua senha.");
        model.addAttribute("usuario", new Usuario(email));
        return "recuperar-senha";
    }

    @PostMapping("/login-nova-senha")
    public String confirmacaoDeRedefinicaoDeSenha(Usuario usuario, ModelMap model) {
        Usuario u = usuarioService.buscarPorEmail(usuario.getEmail());

        // Verifica se o código verificador informado é correto
        if (!usuario.getCodigoVerificador().equals(u.getCodigoVerificador())) {
            model.addAttribute("falha", "Código verificador não confere");
            return "recuperar-senha";
        }

        // Limpa o código verificador do usuário e atualiza a senha
        u.setCodigoVerificador(null);
        usuarioService.alterarSenha(u, usuario.getSenha());

        // Adiciona atributos para exibir mensagem de sucesso na view
        model.addAttribute("alerta", "sucesso");
        model.addAttribute("titulo", "Senha alterada com sucesso");
        model.addAttribute("texto", "Você já pode fazer login no sistema.");

        return "login"; // Redireciona para a página de login após a alteração da senha
    }

}
