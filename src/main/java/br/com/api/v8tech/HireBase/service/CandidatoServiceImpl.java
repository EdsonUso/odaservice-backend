package br.com.api.v8tech.HireBase.service;

import br.com.api.v8tech.HireBase.model.Candidato;
import br.com.api.v8tech.HireBase.model.Competencia;
import br.com.api.v8tech.HireBase.repository.CandidatoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidatoServiceImpl implements CandidatoService{

    private final CandidatoRepository candidatoRepository;

    public CandidatoServiceImpl(CandidatoRepository candidatoRepository) {
        this.candidatoRepository = candidatoRepository;
    }

    @Override
    public Optional<Candidato> buscarCandidatoPorId(Long id) {
        return candidatoRepository.findById(id);
    }

    @Override
    public List<Candidato> buscarCandidatosPorNome(String nomeCandidato) {
        return candidatoRepository.findByNomeContainingIgnoreCase(nomeCandidato);
    }

    @Override
    public List<Candidato> listarCandidatos() {
        return candidatoRepository.findAll();
    }

    @Override
    public List<Candidato> buscarCandidatosPorCompetencia(Competencia competencia) {
        return candidatoRepository.findByCompetenciasContaining(competencia);
    }
}
