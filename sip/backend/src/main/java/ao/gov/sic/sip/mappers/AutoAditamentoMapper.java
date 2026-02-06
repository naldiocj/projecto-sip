package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoAditamentoDTO;
import ao.gov.sic.sip.entities.AutoAditamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoAditamentoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoAditamento autoAditamentoDTOToAutoAditamento(AutoAditamentoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoAditamentoDTO autoAditamentoToAutoAditamentoDTO(AutoAditamento entity);
}
