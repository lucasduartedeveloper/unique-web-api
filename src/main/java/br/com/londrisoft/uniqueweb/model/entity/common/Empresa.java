package br.com.londrisoft.uniqueweb.model.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Empresa {

    public enum Tipo {

        ESCRITORIO,
        EMPRESA

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nsu;

    private Tipo tipo; // Escritório, Empresa

    @JsonIgnore
    private Long escritorioId;

    @Column(length = 200)
    private String razaoSocial;

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

    @Transient
    private String accessToken;

}
