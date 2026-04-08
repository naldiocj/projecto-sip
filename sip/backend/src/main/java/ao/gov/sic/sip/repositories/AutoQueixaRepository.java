package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoQueixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoQueixaRepository extends JpaRepository<AutoQueixa, Long> {
}
