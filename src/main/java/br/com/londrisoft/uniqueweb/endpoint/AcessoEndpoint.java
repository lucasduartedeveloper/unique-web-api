package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.request.LoginRequest;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.dto.response.LoginResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import br.com.londrisoft.uniqueweb.repository.UsuarioRepository;
import br.com.londrisoft.uniqueweb.security.JwtTokenProvider;
import br.com.londrisoft.uniqueweb.service.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AcessoEndpoint {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/entrar")
    public ResponseEntity<?> entrar(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getLogin());
        if (usuario == null) {
            return ResponseEntity.ok(new ApiResponse(false, "Usuário não encontrado."));
        }
        else if (!StringUtil.passwordEncoder().matches(request.getSenha(), usuario.getSenha())) {
            return ResponseEntity.ok(new ApiResponse(false, "Senha incorreta."));
        }
        String accessToken = tokenProvider.generateToken(usuario.getId().toString()+"|0");
        return ResponseEntity.ok(new ApiResponse(new LoginResponse(accessToken, usuario)));
    }
}
