package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.folha.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("select f from Funcionario f where f.empresaId = :empresaId")
    List<Funcionario> findByEmpresaId(@Param("empresaId") Long empresaId);

}
