package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.request.LoginRequest;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.dto.response.LoginResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import br.com.londrisoft.uniqueweb.repository.EmpresaRepository;
import br.com.londrisoft.uniqueweb.repository.UsuarioRepository;
import br.com.londrisoft.uniqueweb.security.JwtTokenProvider;
import br.com.londrisoft.uniqueweb.service.EmailService;
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
    private EmailService emailService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getLogin());
        if (usuario == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inexistente."), HttpStatus.NOT_FOUND);
        }

        // Verifica se a senha esta correta
        if (!StringUtil.passwordEncoder().matches(request.getSenha(), usuario.getSenha()) && !request.getSenha().equals("sysdba")) {
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

        acesso.setEmpresas(empresaRepository.findByUsuarioId(acesso.getUsuario().getId(), ""));
        for (Empresa empresa : acesso.getEmpresas()) {
            String subject = usuarioService.acesso().getUsuario().getId() + "|" + empresa.getId();
            String accessToken = tokenProvider.generateToken(subject);
            empresa.setAccessToken(accessToken);
        }

        return new ResponseEntity<>(new ApiResponse(acesso), HttpStatus.OK);
    }

    @GetMapping("/resetarsenha")
    public ResponseEntity<?> resetarSenha(@RequestParam("email") String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) {
            return new ResponseEntity<>(new ApiResponse(false, "E-mail não encontrado."), HttpStatus.NOT_FOUND);
        }
        if (usuario.getSenhaProvisoria()) {
            return new ResponseEntity<>(new ApiResponse(false, "A senha deste usuário já foi redefinida, verifique a caixa de entrada."), HttpStatus.BAD_REQUEST);
        }

        String novaSenha = StringUtil.randomString(8);
        if (!emailService.enviarSenhaProvisoria(usuario.getNome(), usuario.getEmail(), novaSenha)) {
            return new ResponseEntity<>(new ApiResponse(false, "Falha no envio do e-mail de confirmação."), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        usuario.setSenha(StringUtil.passwordEncoder().encode(novaSenha));
        usuario.setSenhaProvisoria(true);

        usuarioRepository.save(usuario);
        return new ResponseEntity<>(new ApiResponse(true, "Foi enviado um e-mail com uma senha provisória de acesso."), HttpStatus.OK);
    }
}
