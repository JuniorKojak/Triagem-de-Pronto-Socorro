package br.com.triagem.prontosocorro.controller;

import br.com.triagem.prontosocorro.model.Paciente;
import br.com.triagem.prontosocorro.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping("/triagem")
    public ResponseEntity<Paciente> realizarTriagem(@RequestBody Paciente paciente) {
        Paciente novoPaciente = pacienteService.realizarTriagem(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
    }

    @GetMapping("/pacientes/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Long id) {
        Paciente paciente = pacienteService.buscarPorId(id);
        return ResponseEntity.ok(paciente);
    }
}