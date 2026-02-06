package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoAcariacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoAcariacaoRepository extends JpaRepository<AutoAcariacao, Long> {
}
