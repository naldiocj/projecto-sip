package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.CargoDTO;
import ao.gov.sic.sip.entities.Cargo;
import org.mapstruct.Mapper;

@Mapper
public interface CargoMapper {
    Cargo cargoDTOToCargo(CargoDTO dto);

    CargoDTO cargoToCargoDTO(Cargo entity);
}
