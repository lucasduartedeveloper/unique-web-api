package br.com.londrisoft.uniqueweb.audit;

import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import java.util.Date;

public class AuditListener {

    @PrePersist
    public void setCriador(Auditable auditable) {
        Usuario usuario = getUsuario();
        Date data = new Date();
        auditable.setCriador(usuario);
        auditable.setDataCriacao(data);
        auditable.setEditor(usuario);
        auditable.setDataEdicao(data);
    }

    @PreUpdate
    public void setEditor(Auditable auditable) {
        Usuario usuario = getUsuario();
        Date data = new Date();
        auditable.setEditor(usuario);
        auditable.setDataEdicao(data);
    }

    @Transient
    @JsonIgnore
    private Usuario getUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) auth.getDetails();
    }
}