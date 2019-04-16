package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import br.com.londrisoft.uniqueweb.model.entity.common.Permissao;
import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import br.com.londrisoft.uniqueweb.repository.EmpresaRepository;
import br.com.londrisoft.uniqueweb.repository.PermissaoRepository;
import br.com.londrisoft.uniqueweb.repository.UsuarioRepository;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import br.com.londrisoft.uniqueweb.service.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("Duplicates")
@Transactional
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioEndpoint {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PermissaoRepository permissaoRepository;

    @GetMapping
    public ResponseEntity<?> buscar() {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        List<Usuario> usuarios = usuarioRepository.findByEmpresaId(acesso.getEmpresa().getId());
        return new ResponseEntity<>(new ApiResponse(usuarios), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> gravar(@RequestBody Usuario request) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Verifica se o e-mail não é repetido
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());
        if (usuario != null) {
            return new ResponseEntity<>(new ApiResponse(false, "Já existe um usuário cadastrado com este e-mail."), HttpStatus.BAD_REQUEST);
        }

        request.setEmpresaId(acesso.getEmpresa().getId());
        request.setSenha(StringUtil.passwordEncoder().encode(request.getSenha()));
        usuario = usuarioRepository.save(request);
        return new ResponseEntity<>(new ApiResponse(usuario), HttpStatus.OK);
    }

    @GetMapping("/permissoes")
    public ResponseEntity<?> buscarPermissoes(@RequestParam("nsu") String nsu) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Verifica se o usuário existe
        List<Usuario> usuarios = usuarioRepository.findByNsu(acesso.getEmpresa().getId(), nsu);
        if (usuarios.size() == 0 || !acesso.getEmpresa().getId().equals(usuarios.get(0).getEmpresaId())) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inexistente."), HttpStatus.NOT_FOUND);
        }
        return buscarPermissoes(usuarios.get(0));
    }


    @GetMapping("/{id}/permissoes")
    public ResponseEntity<?> buscarPermissoes(@PathVariable Long id) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || !acesso.getEmpresa().getId().equals(usuario.getEmpresaId())) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inexistente."), HttpStatus.NOT_FOUND);
        }
        return buscarPermissoes(usuario);
    }

    private ResponseEntity<?> buscarPermissoes(Usuario usuario) {
        List<Permissao> permissoes = permissaoRepository.findByUsuarioId(usuario.getId());
        return new ResponseEntity<>(new ApiResponse(permissoes), HttpStatus.OK);
    }

    @PostMapping("/permissoes")
    public ResponseEntity<?> gravarPermissoes(@RequestParam(name = "nsu") String nsu, @RequestBody List<Permissao> request) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Verifica se o usuário existe
        List<Usuario> usuarios = usuarioRepository.findByNsu(acesso.getEmpresa().getId(), nsu);
        if (usuarios.size() == 0) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inexistente."), HttpStatus.BAD_REQUEST);
        }
        return gravarPermissoes(usuarios.get(0), request);
    }

    @PostMapping("/{id}/permissoes")
    public ResponseEntity<?> gravarPermissoes(@PathVariable Long id, @RequestBody List<Permissao> request) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Usuário inexistente."), HttpStatus.NOT_FOUND);
        }
        return gravarPermissoes(usuario, request);
    }

    private ResponseEntity<?> gravarPermissoes(Usuario usuario, List<Permissao> permissoes) {
        // Prossegue na gravação das permissões
        permissaoRepository.deleteByUsuarioId(usuario.getId());
        for (Permissao permissao : permissoes) {
            Long empresaId = permissao.getEmpresaId();
            if (permissao.getEmpresaNsu() != null) {
                List<Empresa> empresas = empresaRepository.findByNsu(permissao.getEmpresaNsu());
                if (empresas.size() == 0) {
                    return new ResponseEntity<>(new ApiResponse(false, "Empresa com nsu '" + permissao.getEmpresaNsu() + "' não encontrada."), HttpStatus.NOT_FOUND);
                }
                empresaId = empresas.get(0).getId();
            }
            permissao.setEmpresaId(empresaId);
            permissao.setUsuarioId(usuario.getId());
            permissao = permissaoRepository.save(permissao);
        }
        return new ResponseEntity<>(new ApiResponse("Permissões atualizadas.", permissoes), HttpStatus.OK);
    }
}
