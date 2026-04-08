package ao.gov.sic.sip.repositories;

import ao.gov.sic.sip.entities.Advogado;
import ao.gov.sic.sip.entities.Arguido;
import ao.gov.sic.sip.entities.Participante;
import ao.gov.sic.sip.entities.Queixoso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipanteRepository extends JpaRepository<Participante, Long> {
    List<Participante> findAllByProcessoId(Long processoId);
    List<Participante> findAllByProcesso_Numero(String numero);

    Participante findFirstByQueixoso(Queixoso queixoso);
    Participante findFirstByArguido(Arguido arguido);
    Participante findFirstByAdvogado(Advogado advogado);
}
