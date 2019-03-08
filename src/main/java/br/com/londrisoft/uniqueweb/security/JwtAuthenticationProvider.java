package br.com.londrisoft.uniqueweb.security;

import br.com.londrisoft.uniqueweb.model.entity.Usuario;
import br.com.londrisoft.uniqueweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

/**
 * @author Lucas Duarte for X-Brain in 07/11/18
 */
@Component
public class JwtAuthenticationProvider {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Authentication fromToken(String token) throws UsernameNotFoundException {
        String email = tokenProvider.getSubject(token);
        Usuario usuario = Optional.ofNullable(usuarioRepository.findByEmail(email)).orElse(null);

        if (usuario != null) {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), getAuthorities(usuario));
            authenticationToken.setDetails(usuario);
            return authenticationToken;
        }
        return null;
    }

    private List<GrantedAuthority> getAuthorities(Usuario usuario) {
        return asList(
            new MyGrantedAuthority("LONDRISOFT"),
            new MyGrantedAuthority("ADMIN"),
            new MyGrantedAuthority("USER"));
    }

}
