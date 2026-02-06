package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoDepoimentoDirectoDTO;
import ao.gov.sic.sip.entities.AutoDepoimentoDirecto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoDepoimentoDirectoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoDepoimentoDirecto autoDepoimentoDirectoDTOToAutoDepoimentoDirecto(AutoDepoimentoDirectoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoDepoimentoDirectoDTO autoDepoimentoDirectoToAutoDepoimentoDirectoDTO(AutoDepoimentoDirecto entity);
}
