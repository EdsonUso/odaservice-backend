package br.com.api.v8tech.HireBase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "candidato")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Candidato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;

    @ManyToMany
    @JoinTable(
            name = "candidato_competencias",
            joinColumns = @JoinColumn(name = "candidato_id"),
            inverseJoinColumns = @JoinColumn(name = "competencia_id")
    )
    private Set<Competencia> competencias = new HashSet<>();

    @OneToMany(mappedBy = "candidato", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<VagaCandidato> vagaCandidatos = new HashSet<>();
}
