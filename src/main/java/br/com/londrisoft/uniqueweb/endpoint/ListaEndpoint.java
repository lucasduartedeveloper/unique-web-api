package br.com.londrisoft.uniqueweb.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lista")
public class ListaEndpoint {

    // Escala Horário
    @GetMapping("/dias-semana")
    public ResponseEntity<?> getDiasSemana() {
        return ResponseEntity.ok("Não implementado.");
    }

    // Contrato
    @GetMapping("/cbos")
    public ResponseEntity<?> getCbos() {
        return ResponseEntity.ok("Não implementado.");
    }

    @GetMapping("/tipos-jornada")
    public ResponseEntity<?> getTiposJornada() {
        return ResponseEntity.ok("Não implementado.");
    }

    @GetMapping("/tipos-remuneracao")
    public ResponseEntity<?> getTiposRemuneracao() {
        return ResponseEntity.ok("Não implementado.");
    }

    @GetMapping("/contribuicao-sindical")
    public ResponseEntity<?> getContribuicaoSindical() {
        return ResponseEntity.ok("Não implementado.");
    }

    // Endereço
    @GetMapping("/estados")
    public ResponseEntity<?> getEstados() {
        return ResponseEntity.ok("Não implementado.");
    }

    // Funcionário
    @GetMapping("/racas-cores")
    public ResponseEntity<?> getRacasCores() {
        return ResponseEntity.ok("Não implementado.");
    }

    @GetMapping("/sexos")
    public ResponseEntity<?> getSexos() {
        return ResponseEntity.ok("Não implementado.");
    }

}
