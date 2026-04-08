package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.DiligenciaDTO;
import ao.gov.sic.sip.entities.Diligencia;
import ao.gov.sic.sip.enums.EstadoDiligencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DiligenciaMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    Diligencia diligenciaDTOToDiligencia(DiligenciaDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    DiligenciaDTO diligenciaToDiligenciaDTO(Diligencia entity);
}
