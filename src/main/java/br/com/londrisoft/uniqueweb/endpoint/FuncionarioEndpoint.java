package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.dto.response.ApiResponse;
import br.com.londrisoft.uniqueweb.model.entity.folha.Funcionario;
import br.com.londrisoft.uniqueweb.repository.FuncionarioRepository;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioEndpoint {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @GetMapping
    public ResponseEntity<?> buscar() {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        List<Funcionario> funcionarios = funcionarioRepository.findByEmpresaId(usuarioService.acesso().getEmpresa().getId());
        return new ResponseEntity<>(new ApiResponse(funcionarios), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Procura o funcionário no banco de dados e confirma se ele esta vinculado na empresa selecionada
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null || !acesso.getEmpresa().getId().equals(funcionario.getEmpresaId())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse(funcionario), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> gravar(@RequestBody Funcionario request) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Vincula o funcionário a empresa selecionada grava no banco de dados
        request.setEmpresaId(acesso.getEmpresa().getId());
        Funcionario funcionario = funcionarioRepository.save(request);
        return new ResponseEntity<>(new ApiResponse(funcionario), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Funcionario request) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Vincula o funcionário a empresa selecionada grava no banco de dados
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        request.setId(id);
        request.setEmpresaId(acesso.getEmpresa().getId());
        funcionarioRepository.save(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity<>(new ApiResponse(false, "Nenhuma empresa selecionada."), HttpStatus.BAD_REQUEST);
        }

        // Exclui o funcionário caso ele esteja vinculado a empresa selecionada
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null || !acesso.getEmpresa().getId().equals(funcionario.getId())) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
