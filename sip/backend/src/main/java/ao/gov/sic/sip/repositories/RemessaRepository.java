package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Remessa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RemessaRepository extends JpaRepository<Remessa, Long> {
}
