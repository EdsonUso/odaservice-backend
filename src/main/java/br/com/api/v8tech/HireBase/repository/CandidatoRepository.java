package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.Candidato;
import br.com.api.v8tech.HireBase.model.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
        List<Candidato> findByNomeStartingWithIgnoreCase(String nome);

        @Query("SELECT c FROM Candidato c JOIN c.competencias comp WHERE comp = :competencia")
        List<Candidato> findByCompetencias(@Param("competencia") Competencia competencia);
}
