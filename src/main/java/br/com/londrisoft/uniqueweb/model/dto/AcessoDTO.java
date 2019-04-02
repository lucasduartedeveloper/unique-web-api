package br.com.londrisoft.uniqueweb.model.dto;

import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import lombok.Data;

@Data
public class AcessoDTO {

    private Usuario usuario;
    private Empresa empresa;

}
