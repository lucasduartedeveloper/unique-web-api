package br.com.londrisoft.uniqueweb.audit;

import br.com.londrisoft.uniqueweb.model.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Lucas Duarte for X-Brain in 07/12/18
 */
@MappedSuperclass
@EntityListeners(AuditListener.class)
public abstract class Auditable {

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criador_id", updatable = false)
    private Usuario criador;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_criacao", updatable = false)
    private Date dataCriacao;

    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "editor_id")
    private Usuario editor;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @Column(name = "data_edicao")
    private Date dataEdicao;

    public Usuario getCriador() {
        return criador;
    }

    public void setCriador(Usuario criador) {
        this.criador = criador;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Usuario getEditor() {
        return editor;
    }

    public void setEditor(Usuario editor) {
        this.editor = editor;
    }

    public Date getDataEdicao() {
        return dataEdicao;
    }

    public void setDataEdicao(Date dataEdicao) {
        this.dataEdicao = dataEdicao;
    }
}
