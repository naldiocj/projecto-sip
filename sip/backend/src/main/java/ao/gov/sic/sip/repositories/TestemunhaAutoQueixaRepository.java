package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.TestemunhaAutoQueixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestemunhaAutoQueixaRepository extends JpaRepository<TestemunhaAutoQueixa, Long> {
    TestemunhaAutoQueixa findAllByNome(String nome);
}
