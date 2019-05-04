package br.com.londrisoft.uniqueweb.repository;

import br.com.londrisoft.uniqueweb.model.entity.folha.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    @Query("select f from Funcionario f where f.empresaId = :empresaId and f.documentacao.numeroCpf = :numeroCpf")
    Funcionario findByNumeroCpf(@Param("empresaId") Long empresaId, @Param("numeroCpf") String numeroCpf);

    @Query("select f from Funcionario f where f.empresaId = :empresaId")
    List<Funcionario> findByEmpresaId(@Param("empresaId") Long empresaId);

    @Query("select f from Funcionario f where f.empresaId = :empresaId and (" +
            "f.documentacao.numeroCpf like :texto or " +
            "f.nome like :texto)")
    List<Funcionario> findByTexto(@Param("empresaId") Long empresaId, @Param("texto") String texto);

}
