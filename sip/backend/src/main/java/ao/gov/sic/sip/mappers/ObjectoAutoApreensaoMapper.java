package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ObjectoAutoApreensaoDTO;
import ao.gov.sic.sip.entities.ObjectoAutoApreensao;
import org.mapstruct.Mapper;

@Mapper
public interface ObjectoAutoApreensaoMapper {
    ObjectoAutoApreensao objectoAutoApreensaoDTOToObjectoAutoApreensao(ObjectoAutoApreensaoDTO dto);
    ObjectoAutoApreensaoDTO objectoAutoApreensaoToObjectoAutoApreensaoDTO(ObjectoAutoApreensao entity);
}
