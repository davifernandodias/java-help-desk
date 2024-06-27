package com.systemupdate.beta.service;



import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void enviarEmailCriacaoDeChamado(String destino, UUID codigoBusca) throws MessagingException{
        //enviar email para o cliente
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
        Context context = new Context();
        context.setVariable("titulo", "Chamado cadastrado com sucesso");
        context.setVariable("texto", "Codigo do chamado abaixo");
        
        String codigo = codigoBusca.toString().substring(0, 6);
        context.setVariable("codigo", codigo);

        String html = templateEngine.process("email/confirmacao", context); 
        helper.setTo(destino);
        helper.setText(html,true);
        helper.setSubject("Chamado Criado");
        helper.setFrom("nao-responder@demohelpdesk.com.br");
        
        mailSender.send(message);
    }
    public void enviarPedidoRedefinicaoSenha(String destino, String verificador) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,"UTF-8");
        Context context = new Context();
        context.setVariable("titulo", "Solicitação de redefinição solicitada com sucesso");
        context.setVariable("texto", "Codigo do chamado abaixo");
        
        context.setVariable("verificador", verificador);

        String html = templateEngine.process("email/redefinirsenha", context); 
        helper.setTo(destino);
        helper.setText(html,true);
        helper.setSubject("Refinicao de senha");
        helper.setFrom("nao-responder@demohelpdesk.com.br");
        
        mailSender.send(message);
    }

    
}
