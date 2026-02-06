package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.TipoCrimeDTO;
import ao.gov.sic.sip.entities.TipoCrime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TipoCrimeMapper {
    @Mapping(source = "userId", target = "user.id")
    TipoCrime tipoCrimeDTOToTipoCrime(TipoCrimeDTO dto);

    @Mapping(source = "user.id", target = "userId")
    TipoCrimeDTO tipoCrimeToTipoCrimeDTO(TipoCrime entity);
}
