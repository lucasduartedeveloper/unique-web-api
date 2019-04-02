package br.com.londrisoft.uniqueweb.model.entity.common;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String rezaoSocial;

    @Column(length = 200)
    private String nomeFantasia;

    @Column(length = 14)
    private String cnpj;

    @Column(length = 200)
    private String email;

    @Column(length = 10)
    private String telefoneComercial;

    @Column(length = 11)
    private String telefoneCelular;

    @Column(length = 500)
    private String observacoes;

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Endereco endereco;

}
