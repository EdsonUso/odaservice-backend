package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequisitoRepository extends JpaRepository<Requisito, Long> {

    Optional<Requisito> findByNome(String requisito);
}
