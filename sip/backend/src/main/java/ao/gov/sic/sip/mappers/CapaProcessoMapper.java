package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.CapaProcessoDTO;
import ao.gov.sic.sip.entities.CapaProcesso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CapaProcessoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "livroRegistoId", target = "livroRegisto.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    CapaProcesso capaProcessoDTOToCapaProcesso(CapaProcessoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "livroRegisto.id", target = "livroRegistoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    CapaProcessoDTO capaProcessoToCapaProcessoDTO(CapaProcesso entity);
}
