package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoDeclaracaoEmAditamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoDeclaracaoEmAditamentoRepository extends JpaRepository<AutoDeclaracaoEmAditamento, Long> {
}
