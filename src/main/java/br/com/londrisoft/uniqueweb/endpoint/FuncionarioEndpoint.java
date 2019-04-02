package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.entity.folha.Funcionario;
import br.com.londrisoft.uniqueweb.repository.FuncionarioRepository;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioEndpoint {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FuncionarioRepository funcionarioRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Funcionario request) {
        Funcionario funcionario = funcionarioRepository.save(request);
        return new ResponseEntity<>(funcionario, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Funcionario request) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        request.setId(id);
        funcionarioRepository.save(request);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id).orElse(null);
        if (funcionario == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
