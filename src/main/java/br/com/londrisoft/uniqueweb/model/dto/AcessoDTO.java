package br.com.londrisoft.uniqueweb.model.dto;

import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcessoDTO {

    private Usuario usuario;
    private Empresa empresa;
    private List<Empresa> empresas;

}
