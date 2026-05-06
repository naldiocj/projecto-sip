package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.NotificacaoDTO;
import ao.gov.sic.sip.entities.Notificacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificacaoMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.name", target = "username")
    NotificacaoDTO toDTO(Notificacao entity);

    @Mapping(target = "user", ignore = true)
    Notificacao toEntity(NotificacaoDTO dto);
}
