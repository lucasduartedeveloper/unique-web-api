package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.common.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("select u from Usuario u where upper(u.email) = upper(:email)")
    Usuario findByEmail(String email);

    @Query("select u from Usuario u where u.empresaId = :empresaId")
    List<Usuario> findByEmpresaId(@Param("empresaId") Long empresaId);

    @Query("select u from Usuario u where u.empresaId = :empresaId and u.nsu = :nsu")
    List<Usuario> findByNsu(@Param("empresaId") Long empresaId, @Param("nsu") String nsu);

}