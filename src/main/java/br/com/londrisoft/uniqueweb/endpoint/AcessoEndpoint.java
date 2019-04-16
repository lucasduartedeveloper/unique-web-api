package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.request.LoginRequest;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.dto.response.LoginResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import br.com.londrisoft.uniqueweb.repository.UsuarioRepository;
import br.com.londrisoft.uniqueweb.security.JwtTokenProvider;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import br.com.londrisoft.uniqueweb.service.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api")
public class AcessoEndpoint {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getLogin());
        if (usuario == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inexistente."), HttpStatus.NOT_FOUND);
        }

        // Verifica se a senha esta correta
        if (!StringUtil.passwordEncoder().matches(request.getSenha(), usuario.getSenha())) {
            return new ResponseEntity<>(new ApiResponse(false, "Senha incorreta."), HttpStatus.NOT_FOUND);
        }

        // Bloquear acesso de usuário inativo
        if (usuario.getSituacao() != Usuario.Situacao.ATIVO) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inativo."), HttpStatus.FORBIDDEN);
        }

        // Gera um token de acesso para esse usuário
        String accessToken = tokenProvider.generateToken(usuario.getId() + "|0");
        return new ResponseEntity<>(new ApiResponse(new LoginResponse(accessToken)), HttpStatus.OK);
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> perfil() {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Falha na autenticação."), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new ApiResponse(acesso), HttpStatus.OK);
    }
}
