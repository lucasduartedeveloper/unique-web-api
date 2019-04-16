package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.folha.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {

}
