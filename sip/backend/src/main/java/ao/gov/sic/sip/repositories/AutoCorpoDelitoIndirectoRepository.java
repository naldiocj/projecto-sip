package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoCorpoDelitoIndirecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoCorpoDelitoIndirectoRepository extends JpaRepository<AutoCorpoDelitoIndirecto, Long> {
}
