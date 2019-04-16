package br.com.londrisoft.uniqueweb.model.entity.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8)
    private String cep;

    @Column(length = 2)
    private String estado;

    @Column(length = 200)
    private String cidade;

    @Column(length = 20)
    private String codigoCidade;

    @Column(length = 200)
    private String bairro;

    @Column(length = 200)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 200)
    private String complemento;

    @Column(length = 200)
    private String pontoReferencia;
}
