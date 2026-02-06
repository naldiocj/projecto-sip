package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoInterrogatorioArguidoDTO;
import ao.gov.sic.sip.entities.AutoInterrogatorioArguido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoInterrogatorioArguidoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoInterrogatorioArguido autoInterrogatorioArguidoDTOToAutoInterrogatorioArguido(AutoInterrogatorioArguidoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoInterrogatorioArguidoDTO autoInterrogatorioArguidoToAutoInterrogatorioArguidoDTO(AutoInterrogatorioArguido entity);
}
