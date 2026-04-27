package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.DespachoDTO;
import ao.gov.sic.sip.entities.Despacho;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DespachoMapper {
    @Mapping(source = "processo.id", target = "processoId")
    DespachoDTO toDTO(Despacho entity);

    @Mapping(target = "processo", ignore = true)
    Despacho toEntity(DespachoDTO dto);
}
