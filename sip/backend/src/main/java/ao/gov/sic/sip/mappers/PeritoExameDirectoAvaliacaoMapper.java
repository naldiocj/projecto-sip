package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.PeritoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.entities.PeritoExameDirectoAvaliacao;
import org.mapstruct.Mapper;

@Mapper
public interface PeritoExameDirectoAvaliacaoMapper {
    PeritoExameDirectoAvaliacao peritoExameDirectoAvaliacaoDTOToPeritoExameDirectoAvaliacao(PeritoExameDirectoAvaliacaoDTO dto);
    PeritoExameDirectoAvaliacaoDTO peritoExameDirectoAvaliacaoToPeritoExameDirectoAvaliacaoDTO(PeritoExameDirectoAvaliacao entity);
}
