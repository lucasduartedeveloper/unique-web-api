package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Escala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;
    private Integer tipo;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "escala_id")
    private List<EscalaHorario> horarios;

}

