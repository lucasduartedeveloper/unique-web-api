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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Long empresaId;

    private String diretorio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss", timezone = "Brazil/East")
    private Date dataEnvio = new Date();

    @JsonIgnore
    private Long arquivoId;
    private String arquivoNome;

    private String observacoes;

    @Transient
    private String conteudo;

    private Integer quantidadeDownload = 0;

    public String getLinkDownload() {
        return "http://teste-londrisoft.orienta.com.br:5000/api/documentos/download/" + arquivoId;
    }
}
