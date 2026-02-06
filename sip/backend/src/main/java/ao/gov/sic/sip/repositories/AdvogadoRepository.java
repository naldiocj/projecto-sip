package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Advogado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvogadoRepository extends JpaRepository<Advogado, Long> {
    Advogado findByNumeroCedula(String numeroCedula);
}
