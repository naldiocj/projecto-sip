package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoInterrogatorioEmAditamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoInterrogatorioEmAditamentoRepository extends JpaRepository<AutoInterrogatorioEmAditamento, Long> {
}
