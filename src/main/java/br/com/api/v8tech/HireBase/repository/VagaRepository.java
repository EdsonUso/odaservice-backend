package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.StatusVaga;
import br.com.api.v8tech.HireBase.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByRequisitosNomeContainingIgnoreCase(String requisito);
    List<Vaga> findBySalario(Double salario);
    List<Vaga> findBySetorContainingIgnoreCase(String setor);
    List<Vaga> findByStatus(StatusVaga status);
}
