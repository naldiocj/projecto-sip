package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.QueixosoDTO;
import ao.gov.sic.sip.entities.Queixoso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface QueixosoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "userId", target = "user.id")
    Queixoso queixosoDTOToQueixoso(QueixosoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "user.id", target = "userId")
    QueixosoDTO queixosoToQueixosoDTO(Queixoso entity);
}
