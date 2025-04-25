package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.Candidato;
import br.com.api.v8tech.HireBase.model.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    List<Candidato> findByNomeContainingIgnoreCase(String nome);
    List<Candidato> findByCompetenciasContaining(Competencia competencia);
}
