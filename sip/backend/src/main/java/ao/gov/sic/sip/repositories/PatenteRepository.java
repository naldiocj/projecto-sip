package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatenteRepository extends JpaRepository<Patente, Long> {
}
