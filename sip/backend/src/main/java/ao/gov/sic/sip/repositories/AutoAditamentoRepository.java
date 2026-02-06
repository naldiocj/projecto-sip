package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoAditamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoAditamentoRepository extends JpaRepository<AutoAditamento, Long> {
}
