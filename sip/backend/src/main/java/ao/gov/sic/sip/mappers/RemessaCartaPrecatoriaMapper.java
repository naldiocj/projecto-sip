package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.RemessaCartaPrecatoriaDTO;
import ao.gov.sic.sip.entities.RemessaCartaPrecatoria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface RemessaCartaPrecatoriaMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    RemessaCartaPrecatoria remessaCartaPrecatoriaDTOToRemessaCartaPrecatoria(RemessaCartaPrecatoriaDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    RemessaCartaPrecatoriaDTO remessaCartaPrecatoriaToRemessaCartaPrecatoriaDTO(RemessaCartaPrecatoria entity);
}
