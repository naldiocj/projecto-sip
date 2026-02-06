package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoReconhecimentoFisicoDirectoObjecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoReconhecimentoFisicoDirectoObjectoRepository extends JpaRepository<AutoReconhecimentoFisicoDirectoObjecto, Long> {
}
