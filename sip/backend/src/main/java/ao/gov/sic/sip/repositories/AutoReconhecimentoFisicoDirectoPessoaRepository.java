package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoReconhecimentoFisicoDirectoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoReconhecimentoFisicoDirectoPessoaRepository extends JpaRepository<AutoReconhecimentoFisicoDirectoPessoa, Long> {
}
