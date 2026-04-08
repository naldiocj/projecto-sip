package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Cota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CotaRepository extends JpaRepository<Cota, Long> {
}
