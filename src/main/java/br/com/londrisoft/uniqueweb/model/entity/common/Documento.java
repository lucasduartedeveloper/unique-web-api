package br.com.londrisoft.uniqueweb.model.entity.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Documento {

    public enum Categoria {
        EMPRESA,
        FOLHA,
        CONTABIL,
        FISCAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Long empresaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Brazil/East")
    private Date dataEnvio = new Date();

    private Categoria categoria;

    @JsonIgnore
    private Long arquivoId;
    private String arquivoNome;

    private String observacoes;

    @Transient
    private String conteudo;

    public String getLinkDownload() {
        return "http://teste-londrisoft.orienta.com.br:5000/api/documentos/download/" + arquivoId;
    }
}
