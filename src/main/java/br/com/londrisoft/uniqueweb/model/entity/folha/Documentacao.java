package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
public class Documentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCpf;

    private String numeroRg;
    private String estadoRg; // enum
    private String orgaoRg;
    private Date dataEmissaoRg;

    private String numeroCtps;
    private String estadoCtps; // enum

    private String numeroPis;
    private Date dataEmissaoPis;

    private String tituloEleitor;
    private Date dataEmissaoSecaoEleitoral;

    private String secaoEleitoral;
    private String zonaEleitoral;

    private String numeroCnh;
    private String categoriaCnh;
    private String estadoCnh; // enum

    private Date dataEmissaoCnh;
    private Date dataVencimentoCnh;

    private String numeroReservista;
    private String categoriaReservista;
}
