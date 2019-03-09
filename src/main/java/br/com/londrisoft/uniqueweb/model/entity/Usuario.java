package br.com.londrisoft.uniqueweb.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    @Column
    private String email;

    @Column(length = 100)
    private String senha;
}
