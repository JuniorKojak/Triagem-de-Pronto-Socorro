package br.com.triagem.prontosocorro.service;

import br.com.triagem.prontosocorro.model.Medico;
import br.com.triagem.prontosocorro.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service 
public class MedicoService {

    @Autowired 
    private MedicoRepository medicoRepository;

    public Medico cadastrarMedico(Medico medico) {
    
        Optional<Medico> medicoExistente = medicoRepository.findByCrm(medico.getCrm());
        if (medicoExistente.isPresent()) {
            
            throw new IllegalStateException("Já existe um médico cadastrado com o CRM: " + medico.getCrm());
        }
        
        return medicoRepository.save(medico);
    }

    public Medico atualizarStatusPlantao(Long id, boolean emPlantao) {
        
        Medico medico = medicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico não encontrado com o ID: " + id));
        
        medico.setEmPlantao(emPlantao);
        
        return medicoRepository.save(medico);
    }
}