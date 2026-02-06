package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoDepoimentoIndirecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoDepoimentoIndirectoRepository extends JpaRepository<AutoDepoimentoIndirecto, Long> {
}
