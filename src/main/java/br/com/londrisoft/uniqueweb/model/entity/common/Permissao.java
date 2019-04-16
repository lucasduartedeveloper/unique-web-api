package br.com.londrisoft.uniqueweb.model.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Permissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Transient
    private String empresaNsu;

    @JsonIgnore
    private Long usuarioId;
    private Long empresaId;
    private Boolean temAcesso;


}
