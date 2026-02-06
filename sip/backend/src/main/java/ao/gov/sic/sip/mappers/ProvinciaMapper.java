package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ProvinciaDTO;
import ao.gov.sic.sip.entities.Provincia;
import org.mapstruct.Mapper;

@Mapper
public interface ProvinciaMapper {
    Provincia provinciaDTOToProvincia(ProvinciaDTO dto);
    ProvinciaDTO provinciaToProvinciaDTO(Provincia entity);
}
