package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.MagistradoDTO;
import ao.gov.sic.sip.entities.Magistrado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MagistradoMapper {
    @Mapping(source = "userId", target = "user.id")
    Magistrado magistradoDTOToMagistrado(MagistradoDTO dto);

    @Mapping(source = "user.id", target = "userId")
    MagistradoDTO magistradoToMagistradoDTO(Magistrado entity);
}
