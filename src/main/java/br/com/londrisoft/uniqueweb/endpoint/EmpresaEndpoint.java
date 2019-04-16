package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import br.com.londrisoft.uniqueweb.repository.EmpresaRepository;
import br.com.londrisoft.uniqueweb.repository.UsuarioRepository;
import br.com.londrisoft.uniqueweb.security.JwtTokenProvider;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/empresas")
public class EmpresaEndpoint {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        // Retorna o cadastro da empresa no id informado
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa == null ) {
            return new ResponseEntity<>(new ApiResponse(false, "Empresa inexistente."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(empresa), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> buscar(@RequestParam(name = "nsu", required = false, defaultValue = "") String nsu) {
        // Retorna todos os escritórios se for usado o token de acesso do Unique
        AcessoDTO acesso = usuarioService.acesso();
        List<Empresa> empresas = empresaRepository.findByUsuarioId(acesso.getUsuario().getId(), nsu);

        for (Empresa empresa : empresas) {
            String subject = usuarioService.acesso().getUsuario().getId() + "|" + empresa.getId();
            String acessToken = tokenProvider.generateToken(subject);
            empresa.setAccessToken(acessToken);
        }

        return new ResponseEntity<>(new ApiResponse(empresas), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> gravar(@RequestBody Empresa request) {
        // Grava os dados da empresa no banco de dados gerando um número de id
        Empresa empresa = empresaRepository.save(request);
        return new ResponseEntity<>(new ApiResponse("Empresa cadastrada com sucesso.", empresa), HttpStatus.OK);
    }
}