package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.CartaPrecatoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaPrecatoriaRepository extends JpaRepository<CartaPrecatoria, Long> {
    CartaPrecatoria findAllByNumeroCarta(String numeroCarta);
}
