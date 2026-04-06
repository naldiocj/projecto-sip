package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.EditalDTO;
import ao.gov.sic.sip.entities.Edital;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface EditalMapper {
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    Edital editalDTOToEdital(EditalDTO dto);

    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    EditalDTO editalToEditalDTO(Edital entity);
}
