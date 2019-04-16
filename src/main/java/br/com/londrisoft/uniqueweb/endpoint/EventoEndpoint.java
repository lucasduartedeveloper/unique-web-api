package br.com.londrisoft.uniqueweb.endpoint;

import br.com.londrisoft.uniqueweb.model.dto.AcessoDTO;
import br.com.londrisoft.uniqueweb.model.entity.folha.Evento;
import br.com.londrisoft.uniqueweb.repository.EventoRepository;
import br.com.londrisoft.uniqueweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("Duplicates")
@RestController
@RequestMapping("/api/eventos")
public class EventoEndpoint {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        // Busca o evento com o id informado
        Evento evento = eventoRepository.findById(id).orElse(null);
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> gravar(@RequestBody Evento request) {
        AcessoDTO acesso = usuarioService.acesso();
        if (acesso.getEmpresa() == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        // Vincula o evento a empresa selecionada grava no banco de dados
        request.setEmpresaId(acesso.getEmpresa().getId());
        Evento evento = eventoRepository.save(request);
        return new ResponseEntity<>(evento, HttpStatus.OK);
    }

}
