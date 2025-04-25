package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.VagaCandidato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VagaCandidatoRepository extends JpaRepository <VagaCandidato, Long> {
    Optional<VagaCandidato> findByVagaIdAndCandidatoId(Long vagaId, Long candidatoId);
}
