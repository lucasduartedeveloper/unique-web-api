package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class RegistroPonto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long funcionarioId;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<RegistroPontoDia> marcacoes;

}
