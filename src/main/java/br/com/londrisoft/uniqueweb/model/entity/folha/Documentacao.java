package br.com.londrisoft.uniqueweb.model.entity.folha;

import lombok.Data;
import org.hibernate.annotations.CollectionId;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Documentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11)
    private String numeroCpf;

    @Column(length = 12)
    private String numeroRg;

    @Column(length = 2)
    private String estadoRg;

    @Column(length = 10)
    private String orgaoRg;
    private Date dataEmissaoRg;

    @Column(length = 20)
    private String numeroCtps;

    @Column(length = 2)
    private String estadoCtps;

    @Column(length = 20)
    private String numeroPis;
    private Date dataEmissaoPis;

    @Column(length = 20)
    private String tituloEleitor;
    private Date dataEmissaoSecaoEleitoral;

    @Column(length = 10)
    private String secaoEleitoral;
    @Column(length = 10)
    private String zonaEleitoral;

    @Column(length = 20)
    private String numeroCnh;
    @Column(length = 5)
    private String categoriaCnh;
    @Column(length = 2)
    private String estadoCnh; // enum

    private Date dataEmissaoCnh;
    private Date dataVencimentoCnh;

    @Column(length = 20)
    private String numeroReservista;
    @Column(length = 20)
    private String categoriaReservista;
}
