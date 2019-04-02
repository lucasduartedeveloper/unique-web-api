package br.com.londrisoft.uniqueweb.model.entity.common;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataNascimento;

    @Column(length = 100)
    private String nome;

    @Column(length = 11)
    private String cpf;

    @Column(length = 11)
    private String email;

    @Column(length = 11)
    private String telefoneCelular;

    @Column(length = 1)
    private String temWhatsApp;

    @Column(length = 200)
    private String login;

    @Column(length = 100)
    private String senha;

    private Integer perfil; // Londrisoft, Escritorio, Empresa, Sindico, Condômino
}
