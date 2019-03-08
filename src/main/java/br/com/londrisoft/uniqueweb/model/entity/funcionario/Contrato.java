package br.com.londrisoft.uniqueweb.model.entity.funcionario;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dataAdmissao;
    private String cbo; // enum

    private String funcao;
    private Integer horasMensais;
    private Integer horasSemanais;

    private String tipoRemuneracao; // enum
    private BigDecimal salarioContratual;

    private String tipoJornada;
    private Integer diasExperiencia;
    private Integer diasProrrogacaoExperiencia;

    private String seguroDesempregoAdmissao;
    private String contribuicaoSindical;
}
