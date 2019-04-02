package br.com.londrisoft.uniqueweb.model.dto.response;

import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;
    private Usuario usuario;

    public LoginResponse(String acessToken, Usuario usuario) {
        this.accessToken = acessToken;
        this.usuario = usuario;
    }
}
