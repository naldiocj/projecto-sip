package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoDepoimentoIndirectoDTO;
import ao.gov.sic.sip.entities.AutoDepoimentoIndirecto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoDepoimentoIndirectoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoDepoimentoIndirecto autoDepoimentoIndirectoDTOToAutoDepoimentoIndirecto(AutoDepoimentoIndirectoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoDepoimentoIndirectoDTO autoDepoimentoIndirectoToAutoDepoimentoIndirectoDTO(AutoDepoimentoIndirecto entity);
}
