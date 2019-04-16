package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.common.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {

    @Query("select d from Documento d where d.empresaId = :empresaId and d.categoria = :categoria")
    List<Documento> findByCategoria(@Param("empresaId") Long empresaId, @Param("categoria") Documento.Categoria categoria);

}
