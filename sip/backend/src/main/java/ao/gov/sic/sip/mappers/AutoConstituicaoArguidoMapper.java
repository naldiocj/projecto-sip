package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoConstituicaoArguidoDTO;
import ao.gov.sic.sip.entities.AutoConstituicaoArguido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoConstituicaoArguidoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoConstituicaoArguido autoConstituicaoArguidoDTOToAutoConstituicaoArguido(AutoConstituicaoArguidoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoConstituicaoArguidoDTO autoConstituicaoArguidoToAutoConstituicaoArguidoDTO(AutoConstituicaoArguido entity);
}
