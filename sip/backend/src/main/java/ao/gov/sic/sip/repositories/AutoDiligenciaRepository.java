package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoDiligencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoDiligenciaRepository extends JpaRepository<AutoDiligencia, Long> {
}
