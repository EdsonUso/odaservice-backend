package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.StatusVaga;
import br.com.api.v8tech.HireBase.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

        @Query("SELECT v FROM Vaga v WHERE LOWER(v.setor) LIKE LOWER(CONCAT('%', :setor, '%'))")
        List<Vaga> findBySetorContainingIgnoreCase(@Param("setor") String setor);

        List<Vaga> findByStatus(@Param("status") StatusVaga status);

        @Query("SELECT v FROM Vaga v JOIN v.requisitos r WHERE LOWER(r.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
        List<Vaga> findByRequisitosNomeContainingIgnoreCase(@Param("nome") String nome);

        @Query("SELECT v FROM Vaga v WHERE v.salario BETWEEN :minSalario AND :maxSalario")
        List<Vaga> findBySalarioBetween(@Param("minSalario") Double minSalario, @Param("maxSalario") Double maxSalario);
}

