package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.ArguidoAutoQueixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArguidoAutoQueixaRepository extends JpaRepository<ArguidoAutoQueixa, Long> {
    ArguidoAutoQueixa findAllByNome(String nome);
}
