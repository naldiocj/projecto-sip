package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AdvogadoDTO;
import ao.gov.sic.sip.entities.Advogado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdvogadoMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    Advogado advogadoDTOToAdvogado(AdvogadoDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AdvogadoDTO advogadoToAdvogadoDTO(Advogado entity);
}
