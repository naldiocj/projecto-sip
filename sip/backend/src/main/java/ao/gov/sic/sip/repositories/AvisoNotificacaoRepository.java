package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AvisoNotificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvisoNotificacaoRepository extends JpaRepository<AvisoNotificacao, Long> {
}
