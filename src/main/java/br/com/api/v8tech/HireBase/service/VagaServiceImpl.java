package br.com.api.v8tech.HireBase.service;

import br.com.api.v8tech.HireBase.model.StatusCandidatura;
import br.com.api.v8tech.HireBase.model.StatusVaga;
import br.com.api.v8tech.HireBase.model.Vaga;
import br.com.api.v8tech.HireBase.model.VagaCandidato;
import br.com.api.v8tech.HireBase.repository.VagaCandidatoRepository;
import br.com.api.v8tech.HireBase.repository.VagaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaServiceImpl implements VagaService{

    private final VagaRepository vagaRepository;
    private final VagaCandidatoRepository vagaCandidatoRepository;

    public VagaServiceImpl(VagaRepository vagaRepository, VagaCandidatoRepository vagaCandidatoRepository) {
        this.vagaRepository = vagaRepository;
        this.vagaCandidatoRepository = vagaCandidatoRepository;
    }

    @Override
    public Vaga cadastrarVaga(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    @Override
    public List<Vaga> listarVagas() {
        return vagaRepository.findAll();
    }

    @Override
    public List<Vaga> buscarVagasPorRequisito(String requisito) {
        return vagaRepository.findByRequisitosNomeContainingIgnoreCase(requisito);
    }

    @Override
    public Optional<Vaga> buscarVagaPorId(Long id) {
        return vagaRepository.findById(id);
    }

    @Override
    public List<Vaga> buscarVagaPorSalario(Double salario) {
        return vagaRepository.findBySalario(salario);
    }

    @Override
    public Boolean atualizarStatusVaga(Long id, String statusVaga) {
        Optional<Vaga> vagaOptional = vagaRepository.findById(id);
        if (vagaOptional.isPresent()) {
            Vaga vaga = vagaOptional.get();
            try {
                StatusVaga novoStatus = StatusVaga.valueOf(statusVaga.toUpperCase());
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
    public Boolean atualizarStatusCandidatura(Long vagaId, Long candidatoId, String statusCandidatura) {
        Optional<VagaCandidato> vagaCandidatoOptional = vagaCandidatoRepository.findByVagaIdAndCandidatoId(vagaId, candidatoId);
        if (vagaCandidatoOptional.isPresent()) {
            VagaCandidato vagaCandidato = vagaCandidatoOptional.get();
            try {
                StatusCandidatura novoStatus = StatusCandidatura.valueOf(statusCandidatura.toUpperCase());
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
    public Boolean aprovarCandidato(Long vagaId, Long candidatoId) {
        Optional<VagaCandidato> vagaCandidatoOptional = vagaCandidatoRepository.findByVagaIdAndCandidatoId(vagaId, candidatoId);
        Optional<Vaga> vagaOptional = vagaRepository.findById(vagaId);

        if (vagaCandidatoOptional.isPresent() && vagaOptional.isPresent()) {
            VagaCandidato vagaCandidato = vagaCandidatoOptional.get();
            Vaga vaga = vagaOptional.get();

            vagaCandidato.setStatusCandidatura(StatusCandidatura.APROVADO);
            vaga.setStatus(StatusVaga.FINALIZADA);

            vagaCandidatoRepository.save(vagaCandidato);
            vagaRepository.save(vaga);
            return true;
        }
        return false;
    }

    @Override
    public List<Vaga> buscaVagasPorSetor(String setor) {
        return vagaRepository.findBySetorContainingIgnoreCase(setor);
    }

    @Override
    public List<Vaga> buscarVagaPorStatus(String statusVaga) {
        try {
            StatusVaga status = StatusVaga.valueOf(statusVaga.toUpperCase());
            return vagaRepository.findByStatus(status);
        } catch (IllegalArgumentException e) {
            return List.of();
        }
    }

}
