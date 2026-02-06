package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.PeritoExameDirectoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeritoExameDirectoAvaliacaoRepository extends JpaRepository<PeritoExameDirectoAvaliacao, Long> {
    PeritoExameDirectoAvaliacao findAllByNome(String nome);
}
