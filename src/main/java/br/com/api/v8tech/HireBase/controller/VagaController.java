package br.com.api.v8tech.HireBase.controller;

import br.com.api.v8tech.HireBase.model.Vaga;
import br.com.api.v8tech.HireBase.model.Requisito;
import br.com.api.v8tech.HireBase.repository.RequisitoRepository;
import br.com.api.v8tech.HireBase.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    private final VagaService vagaService;
    private final RequisitoRepository requisitoRepository;

    @Autowired
    public VagaController(VagaService vagaService, RequisitoRepository requisitoRepository) {
        this.vagaService = vagaService;
        this.requisitoRepository = requisitoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vaga> buscarVagaPorId(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        return vagaService.buscarVagaPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping
    public ResponseEntity<List<Vaga>> listarVagas() {
        List<Vaga> vagas = vagaService.listarVagas();
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/buscar-por-setor")
    public ResponseEntity<List<Vaga>> buscarVagasPorSetor(@RequestParam String setor) {
        if (setor.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Vaga> vagas = vagaService.buscaVagasPorSetor(setor.trim());
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/buscar-por-status")
    public ResponseEntity<List<Vaga>> buscarVagaPorStatus(@RequestParam String status) {
        if (status.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<Vaga> vagas = vagaService.buscarVagaPorStatus(status.trim());
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/buscar-por-requisito")
    public ResponseEntity<List<Vaga>> buscarVagasPorRequisito(@RequestParam String requisito) {
        if (requisito.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Requisito> requisitoOpt = requisitoRepository.findByNomeContainingIgnoreCase(requisito.trim());
        if (requisitoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Vaga> vagas = vagaService.buscarVagasPorRequisito(requisitoOpt.get().getNome());
        return ResponseEntity.ok(vagas);
    }

    @GetMapping("/buscar-por-salario")
    public ResponseEntity<List<Vaga>> buscarVagaPorSalario(
            @RequestParam Double minSalario,
            @RequestParam(required = false) Double maxSalario) {
        if (minSalario == null || minSalario < 0) {
            return ResponseEntity.badRequest().build();
        }
        List<Vaga> vagas = vagaService.buscarVagaPorFaixaSalarial(minSalario, maxSalario);
        return ResponseEntity.ok(vagas);
    }

    @PostMapping
    public ResponseEntity<Vaga> cadastrarVaga(@RequestBody Vaga vaga) {
        Vaga vagaCadastrada = vagaService.cadastrarVaga(vaga);
        return ResponseEntity.ok(vagaCadastrada);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatusVaga(@PathVariable Long id, @RequestParam String statusVaga) {
        Boolean atualizado = vagaService.atualizarStatusVaga(id, statusVaga);
        if (atualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{vagaId}/candidatos/{candidatoId}/status")
    public ResponseEntity<Void> atualizarStatusCandidatura(
            @PathVariable Long vagaId,
            @PathVariable Long candidatoId,
            @RequestParam String statusCandidatura) {
        Boolean atualizado = vagaService.atualizarStatusCandidatura(vagaId, candidatoId, statusCandidatura);
        if (atualizado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{vagaId}/candidatos/{candidatoId}/aprovar")
    public ResponseEntity<Void> aprovarCandidato(
            @PathVariable Long vagaId,
            @PathVariable Long candidatoId) {
        Boolean aprovado = vagaService.aprovarCandidato(vagaId, candidatoId);
        if (aprovado) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno: " + ex.getMessage());
    }
}