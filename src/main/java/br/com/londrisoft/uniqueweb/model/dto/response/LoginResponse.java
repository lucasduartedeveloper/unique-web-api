package br.com.londrisoft.uniqueweb.model.dto.response;

import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import lombok.Data;

@Data
public class LoginResponse {

    private String accessToken;

    public LoginResponse(String acessToken) {
        this.accessToken = acessToken;
    }
}
