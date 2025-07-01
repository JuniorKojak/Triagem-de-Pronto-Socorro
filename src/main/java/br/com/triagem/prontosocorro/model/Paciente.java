package br.com.triagem.prontosocorro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private int idade;

    @Column(columnDefinition = "TEXT")
    private String sintomas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gravidade gravidade;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataHoraTriagem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAtendimento status = StatusAtendimento.AGUARDANDO;

    @PrePersist
    protected void onCreate() {
        this.dataHoraTriagem = LocalDateTime.now();
    }
}