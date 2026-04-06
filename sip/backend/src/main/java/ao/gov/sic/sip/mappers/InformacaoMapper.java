package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.InformacaoDTO;
import ao.gov.sic.sip.entities.Informacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InformacaoMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    Informacao informacaoDTOToInformacao(InformacaoDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    InformacaoDTO informacaoToInformacaoDTO(Informacao entity);
}
