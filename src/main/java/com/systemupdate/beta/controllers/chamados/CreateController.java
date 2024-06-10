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
import org.springframework.web.bind.annotation.RequestParam;

import com.systemupdate.beta.models.*;
import com.systemupdate.beta.repository.*;
import com.systemupdate.beta.service.UsuarioService;

@Controller
public class CreateController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private ChamadoJuridicoRepository chamadoJuridicoRepository;

    @Autowired
    private ChamadoInformaticaRepository chamadoInformaticaRepository;

    @Autowired
    private ChamadoFinanceiroRepository chamadoFinanceiroRepository;

    @GetMapping({ "/chamado" })
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
    public String salvarChamado(
            @ModelAttribute("chamado") Chamado chamado,
            @RequestParam(value = "problema", required = false) String problema,
            @RequestParam(value = "equipamento", required = false) String equipamento,
            @RequestParam(value = "advogado", required = false) String advogado,
            @RequestParam(value = "processo", required = false) String processo,
            @RequestParam(value = "valor", required = false) Integer valor,
            @RequestParam(value = "conta", required = false) String conta,
            ModelMap model) {
    
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        Usuario usuario = usuarioService.findByEmail(userEmail);
    
        Colaborador colaborador = usuario.getColaborador();
        chamado.setColaborador(colaborador);
        chamado.setDataCriacao(LocalDateTime.now());
    
        chamadoRepository.save(chamado);
    
        // Salvar detalhes específicos do tipo de chamado
        switch (chamado.getTipoDeChamado()) {
            case 1:
                ChamadoInformatica chamadoInformatica = new ChamadoInformatica();
                chamadoInformatica.setId(chamado.getId());
                chamadoInformatica.setProblema(problema);
                chamadoInformatica.setEquipamento(equipamento);
                chamadoInformatica.setIdTipoChamado(1); // Definindo o tipo de chamado como INFORMATICA
                chamadoInformaticaRepository.save(chamadoInformatica);
                break;
    
                case 2:
                ChamadoFinanceiro chamadoFinanceiro = new ChamadoFinanceiro();
                chamadoFinanceiro.setId(chamado.getId());
                chamadoFinanceiro.setValor(valor);
                chamadoFinanceiro.setConta(conta);
                chamadoFinanceiro.setIdTipoChamado(2); // Definindo o tipo de chamado como FINANCEIRO
                chamadoFinanceiroRepository.save(chamadoFinanceiro);
                break;
    
                case 3:
                ChamadoJuridico chamadoJuridico = new ChamadoJuridico();
                chamadoJuridico.setId(chamado.getId());
                chamadoJuridico.setAdvogado(advogado);
                chamadoJuridico.setProcesso(processo);
                chamadoJuridico.setIdTipoChamado(3); // Definindo o tipo de chamado como JURIDICO
                chamadoJuridicoRepository.save(chamadoJuridico);
                break;
        }
        
    
        model.addAttribute("sucesso", "Chamado salvo com sucesso!");
    
        return "redirect:/chamado";
    }
    
}
