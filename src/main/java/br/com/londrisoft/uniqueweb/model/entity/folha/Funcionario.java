package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String apelido;
    private String email;
    private Date dataNascimento;

    private String nacionalidade; // enum
    private String naturalidade; // enum
    private String estadoNascimento; // enum
    private String cidadeNascimento;

    private String telefoneResidencial;
    private String telefoneCelular;

    private String racaCor; // enum
    private String deficiente; // enum
    private String tipoDeficiencia; // enum

    private String nomePai;
    private String nacionalidadePai; // enum

    private String nomeMae;
    private String nacionalidadeMae; // enum

    private String escolaridade; // enum
    private String sexo; // enum
    private String estadoCivil; // enum
    private String conjuge;
    private String conjugeNaturalizado; // enum
}
