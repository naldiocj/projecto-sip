package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.AutoReconstituicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoReconstituicaoRepository extends JpaRepository<AutoReconstituicao, Long> {
}
