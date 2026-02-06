package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.PatenteDTO;
import ao.gov.sic.sip.entities.Patente;
import org.mapstruct.Mapper;

@Mapper
public interface PatenteMapper {
    Patente patenteDTOToPatente(Patente dto);

    PatenteDTO patenteToPatenteDTO(Patente entity);
}
