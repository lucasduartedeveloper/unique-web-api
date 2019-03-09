package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;

@Data
@Entity
public class EscalaHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaSemana; // enum

    private Time horaEntrada1;
    private Time horaSaida1;

    private Time horaEntrada2;
    private Time horaSaida2;

    private Time horaEntrada3;
    private Time horaSaida3;

    private Time horaEntrada4;
    private Time horaSaida4;

    private Time horaEntrada5;
    private Time horaSaida5;
}
