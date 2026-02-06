package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoInterrogatorioEmAditamentoDTO;
import ao.gov.sic.sip.entities.AutoInterrogatorioEmAditamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoInterrogatorioEmAditamentoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoInterrogatorioEmAditamento autoInterrogatorioEmAditamentoDTOToAutoInterrogatorioEmAditamento(AutoInterrogatorioEmAditamentoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoInterrogatorioEmAditamentoDTO autoInterrogatorioEmAditamentoToAutoInterrogatorioEmAditamentoDTO(AutoInterrogatorioEmAditamento entity);
}
