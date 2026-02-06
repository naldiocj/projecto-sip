package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Queixoso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueixosoRepository extends JpaRepository<Queixoso, Long> {
    Queixoso findByNomeCompletoIgnoreCase(String nome);
}
