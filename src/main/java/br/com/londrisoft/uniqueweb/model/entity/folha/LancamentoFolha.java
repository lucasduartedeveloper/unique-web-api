package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class LancamentoFolha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long funcionarioId;
    private Integer mes;
    private Integer ano;

    private BigDecimal quantidade;
    private BigDecimal valor;

}
