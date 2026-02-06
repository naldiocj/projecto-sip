package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.PeritoExameDirectoDTO;
import ao.gov.sic.sip.entities.PeritoExameDirecto;
import org.mapstruct.Mapper;

@Mapper
public interface PeritoExameDirectoMapper {
    PeritoExameDirecto peritoExameDirectoDTOToPeritoExameDirecto(PeritoExameDirectoDTO dto);
    PeritoExameDirectoDTO peritoExameDirectoToPeritoExameDirectoDTO(PeritoExameDirecto entity);
}
