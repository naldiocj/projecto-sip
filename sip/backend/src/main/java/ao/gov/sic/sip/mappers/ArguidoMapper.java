package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ArguidoDTO;
import ao.gov.sic.sip.entities.Arguido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ArguidoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "processoId", target = "processo.id")
    Arguido arguidoDTOToArguido(ArguidoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "processo.numero", target = "processoNumero")
    ArguidoDTO arguidoToArguidoDTO(Arguido entity);
}
