package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.MandadoDTO;
import ao.gov.sic.sip.entities.Mandado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MandadoMapper {
    @Mapping(target = "arquivo", ignore = true)
    @Mapping(source = "arquivo", target = "arquivoUrl")
    @Mapping(source = "user.id", target = "userId") // Map user.id to userId
    MandadoDTO toDTO(Mandado entity);

    @Mapping(target = "arquivo", ignore = true)
    @Mapping(target = "user", ignore = true) // Ignore user when mapping from DTO to entity
    Mandado toEntity(MandadoDTO dto);
}
