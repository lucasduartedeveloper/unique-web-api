package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.common.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    @Query("select p from Permissao p where p.usuarioId = :usuarioId")
    List<Permissao> findByUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query("select p from Permissao p where p.usuarioId = :usuarioId and p.empresaId = :empresaId")
    Permissao findByUsuarioIdEmpresaId(@Param("usuarioId") Long usuarioId, @Param("empresaId") Long empresaId);

    void deleteByUsuarioId(Long usuarioId);
}
