package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ParticipanteAutoApreensaoDTO;
import ao.gov.sic.sip.entities.ParticipanteAutoApreensao;
import org.mapstruct.Mapper;

@Mapper
public interface ParticipanteAutoApreensaoMapper {
    ParticipanteAutoApreensao participanteAutoApreensaoDTOToParticipanteAutoApreensao(ParticipanteAutoApreensaoDTO dto);
    ParticipanteAutoApreensaoDTO participanteAutoApreensaoToParticipanteAutoApreensaoDTO(ParticipanteAutoApreensao entity);
}
