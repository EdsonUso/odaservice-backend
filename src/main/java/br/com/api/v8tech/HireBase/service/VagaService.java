package br.com.api.v8tech.HireBase.service;

import br.com.api.v8tech.HireBase.model.Vaga;

import java.util.List;
import java.util.Optional;

public interface VagaService {
    /**
     *
     * Cadastra uma vaga na base de dados.
     *
     * @param vaga a vaga a ser cadastrada
     * @return a vaga cadastrada
     */
    Vaga cadastrarVaga(Vaga vaga);

    /**
     *
     * lista todas as vagas da base de dados
     *
     * @return a lista das vagas cadastradas
     */
    List<Vaga> listarVagas();

    /**
     *
     *  busca vagas por requisito
     *
     * @param requisito o requisito a ser buscado
     * @return a lista de vagas
     */
    List<Vaga> buscarVagasPorRequisito(String requisito);

    /**
     *
     * busca vaga por id
     *
     * @param id o id da vaga a ser buscada
     * @return a vaga encontrada
     */
    Optional<Vaga> buscarVagaPorId(Long id);

    /**
     *
     * Busca vaga por salario
     *
     * @param minSalario o salario minimo da vaga a ser buscada
     * @param maxSalario o salario maximo da vaga a ser buscada
     * @return lista de vagas encontradas
     */
    List<Vaga> buscarVagaPorFaixaSalarial(Double minSalario, Double maxSalario);

    /**
     *
     * Atualiza uma vaga
     *
     * @param id o id da vaga a ser atualizada
     * @param statusVaga o novo status da vaga
     * @return true se a vaga foi atualizada com sucesso, false caso contrário
     */
    Boolean atualizarStatusVaga(Long id, String statusVaga);

    /**
     * Atualiza o status de uma candidatura
     *
     * @param vagaId o id da vaga
     * @param candidatoId o id do candidato
     * @param statusCandidatura o novo status da candidatura
     * @return true se a candidatura foi atualizada com sucesso, false caso contrário
     */
    Boolean atualizarStatusCandidatura(Long vagaId, Long candidatoId, String statusCandidatura);

    /**
     * Aprova um candidato para uma vaga, atualizando o status da candidatura para APROVADO
     * e o status da vaga para FINALIZADA
     *
     * @param vagaId o id da vaga
     * @param candidatoId o id do candidato
     * @return true se o candidato foi aprovado com sucesso, false caso contrário
     */
    Boolean aprovarCandidato(Long vagaId, Long candidatoId);

    List<Vaga> buscaVagasPorSetor(String setor);

    List<Vaga> buscarVagaPorStatus(String statusVaga);
}
