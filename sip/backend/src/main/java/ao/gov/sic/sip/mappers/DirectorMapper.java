package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.DirectorDTO;
import ao.gov.sic.sip.entities.Director;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DirectorMapper {
    @Mapping(source = "patenteId", target = "patente.id")
    @Mapping(source = "cargoId", target = "cargo.id")
    @Mapping(source = "direcaoId", target = "direcao.id")
    @Mapping(source = "userId", target = "user.id")
    Director directorDTOToDirector(DirectorDTO dto);

    @Mapping(source = "patente.id", target = "patenteId")
    @Mapping(source = "cargo.id", target = "cargoId")
    @Mapping(source = "direcao.id", target = "direcaoId")
    @Mapping(source = "user.id", target = "userId")
    DirectorDTO directorToDirectorDTO(Director entity);
}
