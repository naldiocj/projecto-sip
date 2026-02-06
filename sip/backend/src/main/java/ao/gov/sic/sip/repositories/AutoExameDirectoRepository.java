package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoExameDirecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoExameDirectoRepository extends JpaRepository<AutoExameDirecto, Long> {
}
