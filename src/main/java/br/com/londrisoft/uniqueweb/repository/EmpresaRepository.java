package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.common.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    List<Empresa> findByTipo(Empresa.Tipo tipo);

    @Query("select e from Empresa e where " +
            "(:usuarioId = 0l or exists" +
            "(select p from Permissao p where p.usuarioId = :usuarioId and p.empresaId = e.id and p.temAcesso = true)) and " +
            "(:nsu = '' or e.nsu = :nsu)")
    List<Empresa> findByUsuarioId(@Param("usuarioId") Long usuarioId, @Param("nsu") String nsu);

    @Query("select e from Empresa e where e.nsu = :nsu")
    List<Empresa> findByNsu(@Param("nsu") String nsu);

}
