package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.SecretariaDTO;
import ao.gov.sic.sip.entities.Secretaria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SecretariaMapper {
    @Mapping(source = "patente.id", target = "patenteId")
    @Mapping(source = "direcao.id", target = "direcaoId")
    @Mapping(source = "user.id", target = "userId")
    SecretariaDTO toDTO(Secretaria entity);

    @Mapping(target = "patente", ignore = true)
    @Mapping(target = "direcao", ignore = true)
    @Mapping(target = "user", ignore = true)
    Secretaria toEntity(SecretariaDTO dto);
}
