package br.com.api.v8tech.HireBase.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "vaga")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(of = "id")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double salario;

    @Column(nullable = false)
    private String setor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusVaga status;

    @Column(nullable = false)
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "vaga_requisito",
            joinColumns = @JoinColumn(name = "vaga_id"),
            inverseJoinColumns = @JoinColumn(name = "requisito_id")
    )
    private Set<Requisito> requisitos = new HashSet<>();

    @OneToMany(mappedBy = "vaga", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VagaCandidato> vagaCandidatos = new HashSet<>();
}
