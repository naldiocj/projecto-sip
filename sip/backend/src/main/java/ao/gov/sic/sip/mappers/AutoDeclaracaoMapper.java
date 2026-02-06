package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoDeclaracaoDTO;
import ao.gov.sic.sip.entities.AutoDeclaracao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoDeclaracaoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoDeclaracao autoDeclaracaoDTOToAutoDeclaracao(AutoDeclaracaoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoDeclaracaoDTO autoDeclaracaoToAutoDeclaracaoDTO(AutoDeclaracao entity);
}
