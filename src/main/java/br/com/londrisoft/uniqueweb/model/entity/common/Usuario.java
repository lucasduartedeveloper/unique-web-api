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
public class Usuario {

    public enum Perfil {
        LONDRISOFT,
        ESCRITORIO,
        EMPRESA,
        SINDICO,
        PORTARIA,
        CONDOMINO
    }

    public enum Situacao {
        ATIVO,
        INATIVO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nsu;

    private Long empresaId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Brazil/East")
    private Date dataNascimento;

    @Column(length = 100)
    private String nome;

    @Column(length = 11)
    private String cpf;

    @Column(length = 100)
    private String email;

    @Column(length = 11)
    private String telefoneCelular;

    @Column(length = 200)
    private String login;

    @Column(length = 100)
    private String senha;

    private Situacao situacao; // Ativo, Inativo

    private Perfil perfil; // Londrisoft, Escritorio, Empresa, Sindico, Portaria, Condômino
}