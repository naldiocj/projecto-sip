package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.PatenteDTO;
import ao.gov.sic.sip.dtos.RoleDTO;
import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.entities.Role;
import org.mapstruct.Mapper;

@Mapper
public interface PatenteMapper {
    Patente patenteToPatenteDTO(PatenteDTO dto);

    PatenteDTO patenteDTOToPatente(Patente entity);
}
