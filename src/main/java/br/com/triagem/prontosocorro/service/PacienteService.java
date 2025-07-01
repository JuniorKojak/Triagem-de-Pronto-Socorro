package br.com.triagem.prontosocorro.service;

import br.com.triagem.prontosocorro.model.Paciente;
import br.com.triagem.prontosocorro.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente realizarTriagem(Paciente paciente) {

        return pacienteRepository.save(paciente);
    }

    public Paciente buscarPorId(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado com o ID: " + id));
    }
}