package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.CapaProcesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapaProcessoRepository extends JpaRepository<CapaProcesso, Long> {
    CapaProcesso findByNumeroExpediente(String numeroExpediente);
}
