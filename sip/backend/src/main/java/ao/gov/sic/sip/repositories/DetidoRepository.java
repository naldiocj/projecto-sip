package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Detido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetidoRepository extends JpaRepository<Detido, Long> {
}
