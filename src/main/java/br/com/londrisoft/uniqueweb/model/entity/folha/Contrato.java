package br.com.londrisoft.uniqueweb.model.entity.folha;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Brazil/East")
    private Date dataAdmissao;

    @Column(length = 20)
    private String cbo;

    private String funcao;
    private Integer horasMensais;
    private Integer horasSemanais;

    private Integer tipoRemuneracao;
    private BigDecimal salarioContratual;

    private Integer tipoJornada;
    private Integer diasExperiencia;
    private Integer diasProrrogacaoExperiencia;

    private String seguroDesempregoAdmissao;
    private Integer contribuicaoSindical;

}
