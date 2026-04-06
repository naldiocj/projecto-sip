package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoReconhecimentoDescricaoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoReconhecimentoDescricaoPessoaRepository extends JpaRepository<AutoReconhecimentoDescricaoPessoa, Long> {
}
