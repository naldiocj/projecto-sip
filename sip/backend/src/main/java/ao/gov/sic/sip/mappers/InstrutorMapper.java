package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.InstrutorDTO;
import ao.gov.sic.sip.entities.Instrutor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface InstrutorMapper {
    @Mapping(source = "patenteId", target = "patente.id")
    @Mapping(source = "cargoId", target = "cargo.id")
    @Mapping(source = "direcaoId", target = "direcao.id")
    @Mapping(source = "userId", target = "user.id")
    Instrutor instrutorDTOToInstrutor(InstrutorDTO dto);

    @Mapping(source = "patente.id", target = "patenteId")
    @Mapping(source = "cargo.id", target = "cargoId")
    @Mapping(source = "direcao.id", target = "direcaoId")
    @Mapping(source = "user.id", target = "userId")
    InstrutorDTO instrutorToInstrutorDTO(Instrutor entity);
}
