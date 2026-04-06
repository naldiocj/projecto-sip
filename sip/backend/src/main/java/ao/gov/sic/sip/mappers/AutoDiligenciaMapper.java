package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoDiligenciaDTO;
import ao.gov.sic.sip.entities.AutoDiligencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoDiligenciaMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoDiligencia autoDiligenciaDTOToAutoDiligencia(AutoDiligenciaDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoDiligenciaDTO autoDiligenciaToAutoDiligenciaDTO(AutoDiligencia entity);
}
