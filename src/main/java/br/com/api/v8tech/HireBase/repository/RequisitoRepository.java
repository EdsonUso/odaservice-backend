package br.com.api.v8tech.HireBase.repository;

import br.com.api.v8tech.HireBase.model.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RequisitoRepository extends JpaRepository<Requisito, Long> {

    @Query("SELECT r FROM Requisito r WHERE LOWER(r.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Optional<Requisito> findByNomeContainingIgnoreCase(@Param("nome") String nome);
}
