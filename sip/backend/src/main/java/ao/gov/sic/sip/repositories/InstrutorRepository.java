package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Instrutor;
import ao.gov.sic.sip.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstrutorRepository extends JpaRepository<Instrutor, Long> {
    Instrutor findByNomeCompletoIgnoreCase(String nomeCompleto);

    Optional<Instrutor> findByUser(User user);
}
