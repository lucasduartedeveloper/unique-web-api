package br.com.londrisoft.uniqueweb.model.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String login;
    private String senha;

}
