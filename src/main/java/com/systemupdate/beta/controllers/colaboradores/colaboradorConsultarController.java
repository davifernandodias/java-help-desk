package com.systemupdate.beta.controllers.colaboradores;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.systemupdate.beta.models.Colaborador;
import com.systemupdate.beta.models.Endereco;
import com.systemupdate.beta.models.InformacaoAdicionaisColaborador;
import com.systemupdate.beta.models.Perfil;
import com.systemupdate.beta.models.PerfilTipo;
import com.systemupdate.beta.models.Usuario;
import com.systemupdate.beta.repository.ColaboradorRepository;
import com.systemupdate.beta.repository.EnderecoRepository;
import com.systemupdate.beta.repository.InformacaoAddRepository;
import com.systemupdate.beta.repository.PerfilRepository;
import com.systemupdate.beta.repository.UsuarioRepository;
import com.systemupdate.beta.security.CustomUserDetails;
import com.systemupdate.beta.service.ColaboradorService;

@Controller
public class ColaboradorConsultarController {

    @Autowired
    ColaboradorService colaboradorService;

    @Autowired
    ColaboradorRepository colaboradorRepository;

    @Autowired
    PerfilRepository perfilRepository;

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    InformacaoAddRepository informacaoAddRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    @GetMapping("/consultarColaborador")
    public String principal(ModelMap model) {
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

        List<Colaborador> colaboradores = colaboradorService.buscarTodos();

        model.addAttribute("colaboradores", colaboradores);
        return "colaborador/consultarColaborador";
    }

    @RequestMapping("/consultarColaborador/{id}")
    public ModelAndView consultarPorIdColaborador(@PathVariable Long id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);
        if (isAuthenticated && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String role = userDetails.getAuthorities().toString();
            model.addAttribute("isAdmin", role.contains("ADMIN"));
        } else {
            model.addAttribute("isAdmin", false);
        }

        List<Colaborador> colaboradores = colaboradorService.buscarTodos();
        Colaborador colaboradorid = colaboradorService.buscarPorId(id);
        model.addAttribute("colaboradores", colaboradores);

        // Verifica se o colaborador encontrado tem informações adicionais
        InformacaoAdicionaisColaborador infoAdicionais = colaboradorid.getInformacaoAdicionais();
        model.addAttribute("infoAdicionais", infoAdicionais);

        Endereco endereco = infoAdicionais.getEndereco();
        model.addAttribute("endereco", endereco);

        // Recupera os perfis do colaborador e os adiciona ao modelo
        List<Perfil> perfis = colaboradorid.getUsuario().getPerfis();
        String perfilDescricao = perfis.stream()
                .map(Perfil::getDescricao)
                .collect(Collectors.joining(", "));
        model.addAttribute("perfilDescricao", perfilDescricao);

        ModelAndView mv = new ModelAndView("colaborador/consultarDetalhesColaborador");
        mv.addObject("colaboradorid", colaboradorid);

        return mv;
    }

    @RequestMapping("/updateColaborador/{id}")
    public ModelAndView updateColaboradorPorId(@PathVariable Long id, ModelMap model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        boolean isAuthenticated = auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);

        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("userEmail", userEmail);
        if (isAuthenticated && auth.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String role = userDetails.getAuthorities().toString();
            model.addAttribute("isAdmin", "ADMIN".equals(role));
        } else {
            model.addAttribute("isAdmin", false);
        }

        List<Colaborador> colaboradores = colaboradorService.buscarTodos();

        Colaborador colaboradorid = colaboradorService.buscarPorId(id);
        model.addAttribute("colaboradores", colaboradores);

        InformacaoAdicionaisColaborador infoAdicionais = colaboradorid.getInformacaoAdicionais();
        model.addAttribute("infoAdicionais", infoAdicionais);

        Endereco endereco = infoAdicionais.getEndereco();
        model.addAttribute("endereco", endereco);

        // Recupera os perfis do colaborador e os adiciona ao modelo
        List<Perfil> perfis = colaboradorid.getUsuario().getPerfis();
        String perfilDescricao = perfis.stream()
                .map(Perfil::getDescricao)
                .collect(Collectors.joining(", "));
        model.addAttribute("perfilDescricao", perfilDescricao);

        ModelAndView mv = new ModelAndView("colaborador/atualizarColaborador");
        mv.addObject("colaboradorid", colaboradorid);
        return mv;
    }
    @PostMapping("/atualizarColaborador")
    public String atualizarColaborador(@ModelAttribute("usuario") Usuario usuario,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String perfilTipo,
            @RequestParam(required = false) String regiao,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Double salario,
            @RequestParam(required = false) String telefone,
            @RequestParam(required = false) String cargo,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) String rg,
            @RequestParam(required = false) String sobrenome,
            @RequestParam(required = false) String logradouro,
            @RequestParam(required = false) String cidade,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) String rua,
            @RequestParam(required = false) String numero,
            @RequestParam(required = false) String cep,
            RedirectAttributes redirectAttributes) {
    
        // Verifica e atualiza o perfil do usuário
        if (perfilTipo != null && !perfilTipo.isEmpty()) {
            PerfilTipo tipo = PerfilTipo.valueOf(perfilTipo);
            Perfil perfil = perfilRepository.findByDescricao(tipo.getDescricao());
            usuario.addPerfil(perfil != null ? perfil : usuario.getPerfis().stream().findFirst().orElse(null));
        }
    
        // Verifica e atualiza o email se necessário
        Usuario existingUser = usuarioRepository.findByEmail(email);
        usuario.setEmail((existingUser != null) ? existingUser.getEmail() : usuario.getEmail());
    
        // Obtém o colaborador associado ao usuário
        Colaborador colaborador = usuario.getColaborador();
        if (colaborador == null) {
            colaborador = new Colaborador();
            colaborador.setUsuario(usuario);
            usuario.setColaborador(colaborador);
        }
    
        // Recupera o cargo atual do colaborador do banco de dados, se necessário
        if (cargo == null) {
            // Recupera o objeto persistido para garantir que todos os campos sejam inicializados corretamente
            colaborador = colaboradorRepository.findById(colaborador.getId()).orElse(colaborador);
        }
    
        // Atualiza as propriedades do colaborador
        colaborador.setNome((nome != null) ? nome : colaborador.getNome());
        colaborador.setCargo((cargo != null) ? cargo : colaborador.getCargo());
        colaborador.setSalario((salario != null) ? salario : colaborador.getSalario());
        colaborador.setRegiao((regiao != null) ? regiao : colaborador.getRegiao());
    
        // Atualiza as informações adicionais do colaborador
        InformacaoAdicionaisColaborador infoadd = colaborador.getInformacaoAdicionais();
        if (infoadd == null) {
            infoadd = new InformacaoAdicionaisColaborador();
            colaborador.setInformacaoAdicionais(infoadd);
            infoadd.setColaborador(colaborador);
        }
        infoadd.setTelefone((telefone != null) ? telefone : infoadd.getTelefone());
        infoadd.setSobrenome((sobrenome != null) ? sobrenome : infoadd.getSobrenome());
    
        // Atualiza o endereço do colaborador
        Endereco endereco = infoadd.getEndereco();
        if (endereco == null) {
            endereco = new Endereco();
            infoadd.setEndereco(endereco);
            endereco.setInformacaoAdicionaisColaborador(infoadd);
        }
        endereco.setLogradouro((logradouro != null) ? logradouro : endereco.getLogradouro());
        endereco.setCidade((cidade != null) ? cidade : endereco.getCidade());
        endereco.setEstado((estado != null) ? estado : endereco.getEstado());
        endereco.setBairro((bairro != null) ? bairro : endereco.getBairro());
        endereco.setRua((rua != null) ? rua : endereco.getRua());
        endereco.setNumero((numero != null) ? numero : endereco.getNumero());
        endereco.setCep((cep != null) ? cep : endereco.getCep());
    
        // Salva o usuário e o colaborador no banco de dados
        usuarioRepository.save(usuario);
        colaboradorRepository.save(colaborador); // Salva o colaborador separadamente, se necessário
        informacaoAddRepository.save(infoadd);
        enderecoRepository.save(endereco);
    
        // Redireciona para a página adequada após salvar, incluindo o ID do colaborador
        redirectAttributes.addAttribute("id", colaborador.getId());
        return "redirect:/consultarColaborador/{id}";
    }
    
    

}