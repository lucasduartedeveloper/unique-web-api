package br.com.londrisoft.uniqueweb.service;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public AcessoDTO acesso() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getDetails().getClass().getName().contains("AcessoDTO")) {
            return (AcessoDTO) auth.getDetails();
        }
        else return null;
    }

}
