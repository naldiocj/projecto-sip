package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.MunicipioDTO;
import ao.gov.sic.sip.entities.Municipio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MunicipioMapper {
    @Mapping(source = "provinciaId", target = "provincia.id")
    Municipio municipioDTOToMunicipio(MunicipioDTO dto);

    @Mapping(source = "provincia.id", target = "provinciaId")
    MunicipioDTO municipioToMunicipioDTO(Municipio entity);
}
