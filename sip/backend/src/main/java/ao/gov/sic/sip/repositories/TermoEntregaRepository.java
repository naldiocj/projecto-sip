package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.TermoEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermoEntregaRepository extends JpaRepository<TermoEntrega, Long> {
}
