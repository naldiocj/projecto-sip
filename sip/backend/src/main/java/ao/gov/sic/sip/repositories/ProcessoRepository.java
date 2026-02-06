package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Processo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Processo findByNumero(String numero);
}
