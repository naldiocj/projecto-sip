package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    Provincia findByNomeIgnoreCase(String nome);
}
