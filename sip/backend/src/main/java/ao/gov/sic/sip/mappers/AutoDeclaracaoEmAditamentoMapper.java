package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoDeclaracaoEmAditamentoDTO;
import ao.gov.sic.sip.entities.AutoDeclaracaoEmAditamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoDeclaracaoEmAditamentoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoDeclaracaoEmAditamento autoDeclaracaoEmAditamentoDTOToAutoDeclaracaoEmAditamento(AutoDeclaracaoEmAditamentoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoDeclaracaoEmAditamentoDTO autoDeclaracaoEmAditamentoToAutoDeclaracaoEmAditamentoDTO(AutoDeclaracaoEmAditamento entity);
}
