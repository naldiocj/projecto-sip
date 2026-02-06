package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoInterrogatorioArguido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoInterrogatorioArguidoRepository extends JpaRepository<AutoInterrogatorioArguido, Long> {
}
