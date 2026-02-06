package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.DirecaoDTO;
import ao.gov.sic.sip.entities.Direcao;
import org.mapstruct.Mapper;

@Mapper
public interface DirecaoMapper {
    Direcao direcaoDTOToDirecao(DirecaoDTO dto);

    DirecaoDTO direcaoToDirecaoDTO(Direcao entity);
}
