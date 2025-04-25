package br.com.api.v8tech.HireBase.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "vaga_candidato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VagaCandidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    @JsonBackReference
    private Vaga vaga;

    @ManyToOne
    @JoinColumn(name = "candidato_id", nullable = false)
    private Candidato candidato;

    private LocalDate dataCandidatura;

    @Enumerated(EnumType.STRING)
    private StatusCandidatura statusCandidatura;


}
