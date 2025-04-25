package br.com.api.v8tech.HireBase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "competencia")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Competencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String especializacao;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;


    @ManyToMany(mappedBy = "competencias")
    @JsonIgnore
    private Set<Candidato> candidatos;

}
