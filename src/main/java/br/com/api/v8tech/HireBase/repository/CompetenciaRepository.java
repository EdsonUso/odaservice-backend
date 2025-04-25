package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetenciaRepository extends JpaRepository<Competencia, Long> {
    Optional<Competencia> findByEspecializacao(String especializacao);
}
