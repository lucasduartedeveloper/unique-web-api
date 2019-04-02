package br.com.londrisoft.uniqueweb.model.entity.folha;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Time;
import java.util.Date;

@Data
@Entity
public class RegistroPontoDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long funcionarioId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "Brazil/East")
    private Date data;

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

    private Time horaEntrada6;
    private Time horaSaida6;

    private Time horaEntrada7;
    private Time horaSaida7;

}
