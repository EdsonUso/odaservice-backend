package br.com.api.v8tech.HireBase.service;

import br.com.api.v8tech.HireBase.model.Candidato;
import br.com.api.v8tech.HireBase.model.Competencia;

import java.util.List;
import java.util.Optional;

public interface CandidatoService {
    /**
     *
     * Busca um candidato no sistema pelo id
     *
     * @param id o id do candidato a ser buscado
     * @return o candidato encontrado
     */
    Optional<Candidato> buscarCandidatoPorId(Long id);

    /**
     *
     * Busca um candidato no sistema pelo nome
     *
     * @param nomeCandidato o nome do candidato a ser buscado
     * @return os candidatos encontrados
     */
    List<Candidato> buscarCandidatosPorNome(String nomeCandidato);

    /**
     *
     * Busca todos os candidatos do sistema
     *
     * @return todos os candidatos
     */

    List<Candidato> listarCandidatos();

    /**
     *
     *Busca candidatos por especialização
     *
     * @param competencia a especialização do candidato
     * @return os candidatos encontrados
     */
    List<Candidato> buscarCandidatosPorCompetencia(Competencia competencia);


}
