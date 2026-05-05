package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.CreateDetidoDTO;
import ao.gov.sic.sip.dtos.DetidoDTO;
import ao.gov.sic.sip.entities.Detido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetidoMapper {
    DetidoDTO toDTO(Detido entity);
    Detido toEntity(CreateDetidoDTO dto);
}
