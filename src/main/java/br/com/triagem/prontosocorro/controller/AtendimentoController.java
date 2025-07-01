package br.com.triagem.prontosocorro.controller;

import br.com.triagem.prontosocorro.model.Paciente;
import br.com.triagem.prontosocorro.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/atendimento")
public class AtendimentoController {

    @Autowired
    private AtendimentoService atendimentoService;

    @PostMapping("/proximo")
    public ResponseEntity<?> buscarProximoPaciente() {
        Optional<Paciente> proximoPaciente = atendimentoService.chamarProximoPaciente();

        return proximoPaciente.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}