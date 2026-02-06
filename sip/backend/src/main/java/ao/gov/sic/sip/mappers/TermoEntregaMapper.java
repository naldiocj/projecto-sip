package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.TermoEntregaDTO;
import ao.gov.sic.sip.entities.TermoEntrega;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TermoEntregaMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    TermoEntrega termoEntregaDTOToTermoEntrega(TermoEntregaDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    TermoEntregaDTO termoEntregaToTermoEntregaDTO(TermoEntrega entity);
}
