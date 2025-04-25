package br.com.api.v8tech.HireBase.controller;

import br.com.api.v8tech.HireBase.model.Candidato;
import br.com.api.v8tech.HireBase.model.Competencia;
import br.com.api.v8tech.HireBase.repository.CompetenciaRepository;
import br.com.api.v8tech.HireBase.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidatos")
public class CandidatoController {

    private final CandidatoService candidatoService;
    private final CompetenciaRepository competenciaRepository;

    @Autowired
    public CandidatoController(CandidatoService candidatoService, CompetenciaRepository competenciaRepository) {
        this.candidatoService = candidatoService;
        this.competenciaRepository = competenciaRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidato> buscarCandidatoPorId(@PathVariable Long id) {
        return candidatoService.buscarCandidatoPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar-por-nome")
    public ResponseEntity<List<Candidato>> buscarCandidatoPorNome(@RequestParam String nome) {
        List<Candidato> candidatos = candidatoService.buscarCandidatosPorNome(nome);
        return ResponseEntity.ok(candidatos);
    }

    @GetMapping
    public ResponseEntity<List<Candidato>> listarCandidatos() {
        return ResponseEntity.ok(candidatoService.listarCandidatos());
    }

    @GetMapping("/buscar-por-competencia")
    public ResponseEntity<List<Candidato>> listarCandidatosPorCompetencia(@RequestParam String especializacao) {
        Optional<Competencia> especializacaoOpt = competenciaRepository.findByEspecializacao(especializacao);

        if (especializacaoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Candidato> candidatos = candidatoService.buscarCandidatosPorCompetencia(especializacaoOpt.get());
        return ResponseEntity.ok(candidatos);
    }
}

