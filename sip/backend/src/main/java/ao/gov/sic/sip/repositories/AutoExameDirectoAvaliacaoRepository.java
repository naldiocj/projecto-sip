package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoExameDirectoAvaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoExameDirectoAvaliacaoRepository extends JpaRepository<AutoExameDirectoAvaliacao, Long> {
}
