package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AvisoNotificacaoDTO;
import ao.gov.sic.sip.entities.AvisoNotificacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AvisoNotificacaoMapper {
    @Mapping(source = "arguidoId", target = "arguido.id")
    @Mapping(source = "enderecoDestinoId", target = "enderecoDestino.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AvisoNotificacao avisoNotificacaoDTOToAvisoNotificacao(AvisoNotificacaoDTO dto);

    @Mapping(source = "arguido.id", target = "arguidoId")
    @Mapping(source = "enderecoDestino.id", target = "enderecoDestinoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AvisoNotificacaoDTO avisoNotificacaoToAvisoNotificacaoDTO(AvisoNotificacao entity);
}
