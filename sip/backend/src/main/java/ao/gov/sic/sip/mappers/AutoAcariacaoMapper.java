package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoAcariacaoDTO;
import ao.gov.sic.sip.entities.AutoAcariacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoAcariacaoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoAcariacao autoAcariacaoDTOToAutoAcariacao(AutoAcariacaoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoAcariacaoDTO autoAcariacaoToAutoAcariacaoDTO(AutoAcariacao entity);
}
