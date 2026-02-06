package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoDepoimentoDirecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoDepoimentoDirectoRepository extends JpaRepository<AutoDepoimentoDirecto, Long> {
}
