package br.com.triagem.prontosocorro.service;

import br.com.triagem.prontosocorro.model.Paciente;
import br.com.triagem.prontosocorro.model.StatusAtendimento;
import br.com.triagem.prontosocorro.repository.MedicoRepository;
import br.com.triagem.prontosocorro.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AtendimentoService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public Optional<Paciente> chamarProximoPaciente() {
        long medicosEmPlantao = medicoRepository.countByEmPlantao(true);
        if (medicosEmPlantao == 0) {
            return Optional.empty();
        }

        Optional<Paciente> proximoOptional = pacienteRepository
                .findFirstByStatusOrderByPrioridadeAscGravidadeAscDataHoraTriagemAsc(StatusAtendimento.AGUARDANDO);

        if (proximoOptional.isPresent()) {
            Paciente proximo = proximoOptional.get();
            proximo.setStatus(StatusAtendimento.EM_ATENDIMENTO);
            pacienteRepository.save(proximo);
            return Optional.of(proximo);
        }

        return Optional.empty();
    }
}