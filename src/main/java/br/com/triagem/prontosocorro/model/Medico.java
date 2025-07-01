package br.com.triagem.prontosocorro.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "medicos") 
@Getter                  
@Setter                  
@NoArgsConstructor       
@AllArgsConstructor      
public class Medico {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false, unique = true)
    private String crm;

    private boolean emPlantao;
}