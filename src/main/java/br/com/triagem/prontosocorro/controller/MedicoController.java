package br.com.triagem.prontosocorro.controller;

import br.com.triagem.prontosocorro.model.Medico;
import br.com.triagem.prontosocorro.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; 

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @PostMapping
    public ResponseEntity<Medico> cadastrarMedico(@RequestBody Medico medico) {
        Medico novoMedico = medicoService.cadastrarMedico(medico);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoMedico);
    }

    @PutMapping("/{id}/plantao")
    public ResponseEntity<Medico> atualizarStatusPlantao(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
    
        boolean emPlantao = request.get("emPlantao");
        Medico medicoAtualizado = medicoService.atualizarStatusPlantao(id, emPlantao);
        return ResponseEntity.ok(medicoAtualizado);
    }
}