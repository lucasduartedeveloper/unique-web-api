package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long empresaId;

    private String nsu;

    @Column(length = 1)
    private String tipo; // P, D
    private String descricao;

}
