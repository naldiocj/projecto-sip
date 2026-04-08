package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.ParticipanteAutoApreensao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteAutoApreensaoRepository extends JpaRepository<ParticipanteAutoApreensao, Long> {
    ParticipanteAutoApreensao findAllByNome(String nome);
}
