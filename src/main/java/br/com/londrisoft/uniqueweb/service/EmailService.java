package br.com.londrisoft.uniqueweb.service;

import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

/**
 * @author Lucas Duarte for X-Brain in 16/11/18
 */
@Component
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    public Boolean enviarSenhaProvisoria(String nome, String endereco, String senhaProvisoria) {
        String assunto = "Nova senha do Escritório Virtual";
        String html = "<p>Olá "+ nome +",<br />sua senha provisória para acesso ao sistema é: <b>"+senhaProvisoria+"</b></p>";
        return enviar(endereco, assunto, html);
    }

    public Boolean enviarDadosDeAcesso(Usuario usuario, String senha) {
        String assunto = "Bem-vindo ao Gestão Saúde";
        String html = "<p>Olá "+ usuario.getNome() +
                ",<br />utilize esse email para entrar no sistema: <b>" + usuario.getEmail().toLowerCase() +
                "</b><br />sua senha provisória é: <b>" + senha +
                "</b><br />acesso: http://gestaosaude.xbrain.com.br/</p>";
        return enviar(usuario.getEmail(), assunto, html);
    }

    private Boolean enviar(String endereco, String assunto, String html) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper( mail );

            helper.setTo(endereco);
            helper.setSubject(assunto);
            helper.setText(html ,true);
            mailSender.send(mail);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
