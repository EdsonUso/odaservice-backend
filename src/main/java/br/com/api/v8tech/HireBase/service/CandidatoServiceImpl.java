package br.com.api.v8tech.HireBase.service;

import br.com.api.v8tech.HireBase.model.Candidato;
import br.com.api.v8tech.HireBase.model.Competencia;
import br.com.api.v8tech.HireBase.repository.CandidatoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CandidatoServiceImpl implements CandidatoService {

    private final CandidatoRepository candidatoRepository;

    @Autowired
    public CandidatoServiceImpl(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    @Override
    public Optional<Candidato> buscarCandidatoPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return candidatoRepository.findById(id);
    }

    @Override
    public List<Candidato> buscarCandidatosPorNome(String nomeCandidato) {
        if (nomeCandidato == null || nomeCandidato.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return candidatoRepository.findByNomeStartingWithIgnoreCase(nomeCandidato.trim());
    }

    @Override
    public List<Candidato> listarCandidatos() {
        return candidatoRepository.findAll();
    }

    @Override
    public List<Candidato> buscarCandidatosPorCompetencia(Competencia competencia) {
        if (competencia == null) {
            return Collections.emptyList();
        }
        return candidatoRepository.findByCompetencias(competencia);
    }
}
