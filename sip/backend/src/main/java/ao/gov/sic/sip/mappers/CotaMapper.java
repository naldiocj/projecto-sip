package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.CotaDTO;
import ao.gov.sic.sip.entities.Cota;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CotaMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    Cota cotaDTOToCota(CotaDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    CotaDTO cotaToCotaDTO(Cota entity);
}
