package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Patente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatenteRepository extends JpaRepository<Patente, Long> {
}
