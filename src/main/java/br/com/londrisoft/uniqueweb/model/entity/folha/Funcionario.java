package br.com.londrisoft.uniqueweb.model.entity.folha;

import br.com.londrisoft.uniqueweb.model.entity.common.Endereco;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private Long empresaId;

    @Column(length = 100)
    private String nome;

    @Column(length = 100)
    private String apelido;

    @Column(length = 200)
    private String email;

    private Date dataNascimento;
    private Integer nacionalidade;

    @Column(length = 1)
    private String naturalizado; // S, N

    @Column(length = 2)
    private String estadoNascimento;
    @Column(length = 200)
    private String cidadeNascimento;

    @Column(length = 10)
    private String telefoneResidencial;
    @Column(length = 11)
    private String telefoneCelular;

    private Integer racaCor;

    @Column(length = 1)
    private String deficiente; // S, N
    private Integer tipoDeficiencia;

    private String nomePai;
    private Integer nacionalidadePai;

    private String nomeMae;
    private Integer nacionalidadeMae;

    private Integer escolaridade;

    @Column(length = 1)
    private String sexo; // M, F
    private Integer estadoCivil;

    @Column(length = 100)
    private String conjuge;

    @Column(length = 1)
    private String conjugeNaturalizado; // S, N

    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Documentacao documentacao;
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Endereco endereco;
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private Contrato contrato;
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private EscalaHorario escalaHorario;
}
