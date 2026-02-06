package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Instrutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Instrutor findByNomeCompletoIgnoreCase(String nomeCompleto);
}
