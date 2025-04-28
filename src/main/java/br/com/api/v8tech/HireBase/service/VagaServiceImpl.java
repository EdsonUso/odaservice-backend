package br.com.api.v8tech.HireBase.service;

import br.com.api.v8tech.HireBase.model.StatusCandidatura;
import br.com.api.v8tech.HireBase.model.StatusVaga;
import br.com.api.v8tech.HireBase.model.Vaga;
import br.com.api.v8tech.HireBase.model.VagaCandidato;
import br.com.api.v8tech.HireBase.repository.VagaCandidatoRepository;
import br.com.api.v8tech.HireBase.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VagaServiceImpl implements VagaService {

    private final VagaRepository vagaRepository;
    private final VagaCandidatoRepository vagaCandidatoRepository;

    @Autowired
    public VagaServiceImpl(VagaRepository vagaRepository, VagaCandidatoRepository vagaCandidatoRepository) {
        this.vagaRepository = vagaRepository;
        this.vagaCandidatoRepository = vagaCandidatoRepository;
    }

    @Override
    @Transactional
    public Vaga cadastrarVaga(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    @Override
    public List<Vaga> listarVagas() {
        return vagaRepository.findAll();
    }

    @Override
    public List<Vaga> buscarVagasPorRequisito(String requisito) {
        if (requisito == null || requisito.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return vagaRepository.findByRequisitosNomeContainingIgnoreCase(requisito.trim());
    }

    @Override
    public Optional<Vaga> buscarVagaPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return vagaRepository.findById(id);
    }

    @Override
    public List<Vaga> buscarVagaPorFaixaSalarial(Double minSalario, Double maxSalario) {
        if (minSalario == null || minSalario < 0) {
            return Collections.emptyList();
        }
        Double max = (maxSalario != null && maxSalario >= minSalario) ? maxSalario : Double.MAX_VALUE;
        return vagaRepository.findBySalarioBetween(minSalario, max);
    }

    @Override
    @Transactional
    public Boolean atualizarStatusVaga(Long id, String statusVaga) {
        if (id == null || id <= 0 || statusVaga == null || statusVaga.trim().isEmpty()) {
            return false;
        }
        Optional<Vaga> vagaOptional = vagaRepository.findById(id);
        if (vagaOptional.isPresent()) {
            Vaga vaga = vagaOptional.get();
            try {
                StatusVaga novoStatus = StatusVaga.valueOf(statusVaga.trim().toUpperCase());
                vaga.setStatus(novoStatus);
                vagaRepository.save(vaga);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean atualizarStatusCandidatura(Long vagaId, Long candidatoId, String statusCandidatura) {

        if (vagaId == null || candidatoId == null || statusCandidatura == null || statusCandidatura.trim().isEmpty()) {
            return false;
        }
        Optional<VagaCandidato> vagaCandidatoOptional = vagaCandidatoRepository.findByVagaIdAndCandidatoId(vagaId, candidatoId);
        if (vagaCandidatoOptional.isPresent()) {
            VagaCandidato vagaCandidato = vagaCandidatoOptional.get();
            try {
                StatusCandidatura novoStatus = StatusCandidatura.valueOf(statusCandidatura.trim().toUpperCase());
                vagaCandidato.setStatusCandidatura(novoStatus);
                vagaCandidatoRepository.save(vagaCandidato);
                return true;
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean aprovarCandidato(Long vagaId, Long candidatoId) {
        if (vagaId == null || candidatoId == null) {
            return false;
        }
        Optional<VagaCandidato> vagaCandidatoOptional = vagaCandidatoRepository.findByVagaIdAndCandidatoId(vagaId, candidatoId);
        Optional<Vaga> vagaOptional = vagaRepository.findById(vagaId);

        if (vagaCandidatoOptional.isPresent() && vagaOptional.isPresent()) {
            VagaCandidato vagaCandidato = vagaCandidatoOptional.get();
            Vaga vaga = vagaOptional.get();

            vagaCandidato.setStatusCandidatura(StatusCandidatura.APROVADO);
            vaga.setStatus(StatusVaga.FINALIZADA);

            // Rejeitar outros candidatos
            vaga.getVagaCandidatos().stream()
                    .filter(vc -> !vc.getCandidato().getId().equals(candidatoId))
                    .forEach(vc -> vc.setStatusCandidatura(StatusCandidatura.REPROVADO));

            vagaCandidatoRepository.save(vagaCandidato);
            vagaRepository.save(vaga);
            return true;
        }
        return false;
    }

    @Override
    public List<Vaga> buscaVagasPorSetor(String setor) {
        if (setor == null || setor.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return vagaRepository.findBySetorContainingIgnoreCase(setor.trim());
    }

    @Override
    public List<Vaga> buscarVagaPorStatus(String statusVaga) {
        if (statusVaga == null || statusVaga.trim().isEmpty()) {
            return Collections.emptyList();
        }
        try {
            StatusVaga status = StatusVaga.valueOf(statusVaga.trim().toUpperCase());
            return vagaRepository.findByStatus(status);
        } catch (IllegalArgumentException e) {
            return Collections.emptyList();
        }
    }
}