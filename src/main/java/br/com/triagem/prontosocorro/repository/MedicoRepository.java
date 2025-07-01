package br.com.triagem.prontosocorro.repository;

import br.com.triagem.prontosocorro.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository 
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    long countByEmPlantao(boolean emPlantao);

    Optional<Medico> findByCrm(String crm);
}