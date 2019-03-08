package br.com.londrisoft.uniqueweb.model.entity.funcionario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String estado; // enum
    private String cidade;
    private String cep;
    private String bairro;
    private String logradouro;
    private String numero;

}
