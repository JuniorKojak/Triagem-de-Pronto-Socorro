package br.com.triagem.prontosocorro.repository;

import br.com.triagem.prontosocorro.model.Paciente;
import br.com.triagem.prontosocorro.model.StatusAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findFirstByStatusOrderByPrioridadeAscGravidadeAscDataHoraTriagemAsc(StatusAtendimento status);

}