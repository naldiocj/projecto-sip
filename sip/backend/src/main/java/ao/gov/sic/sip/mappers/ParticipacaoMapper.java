package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ParticipacaoDTO;
import ao.gov.sic.sip.entities.Participacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ParticipacaoMapper {
    @Mapping(source = "queixosoId", target = "queixoso.id")
    @Mapping(source = "autoApreensaoId", target = "autoApreensao.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    Participacao participacaoDTOToParticipacao(ParticipacaoDTO dto);

    @Mapping(source = "queixoso.id", target = "queixosoId")
    @Mapping(source = "autoApreensao.id", target = "autoApreensaoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    ParticipacaoDTO participacaoToParticipacaoDTO(Participacao entity);
}
