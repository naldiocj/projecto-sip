package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.PedidoComparencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoComparenciaRepository extends JpaRepository<PedidoComparencia, Long> {
}
