package br.com.londrisoft.uniqueweb.security;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import br.com.londrisoft.uniqueweb.repository.EmpresaRepository;
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

    @Autowired
    private EmpresaRepository empresaRepository;

    public Authentication fromToken(String token) throws UsernameNotFoundException {
        String[] subject = tokenProvider.getSubject(token).split("\\|");

        if (subject.length == 2) {
            Long usuarioId = Long.parseLong(subject[0]);
            Long empresaId = Long.parseLong(subject[1]);

            Usuario usuario = null;
            Empresa empresa = empresaRepository.findById(empresaId).orElse(null);
            if (usuarioId == 0l) {
                usuario = new Usuario();
                usuario.setId(0l);
                usuario.setEmpresaId(0l);
                usuario.setNome("MASTER");
            }
            else {
                usuario = usuarioRepository.findById(usuarioId).orElse(null);
            }

            AcessoDTO acesso = new AcessoDTO();
            acesso.setUsuario(usuario);
            acesso.setEmpresa(empresa);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), getAuthorities(usuario));
            authenticationToken.setDetails(acesso);
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
