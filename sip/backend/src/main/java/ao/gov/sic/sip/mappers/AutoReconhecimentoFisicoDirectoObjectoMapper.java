package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoObjectoDTO;
import ao.gov.sic.sip.entities.AutoReconhecimentoFisicoDirectoObjecto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoReconhecimentoFisicoDirectoObjectoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoReconhecimentoFisicoDirectoObjecto autoReconhecimentoFisicoDirectoObjectoDTOToAutoReconhecimentoFisicoDirectoObjecto(AutoReconhecimentoFisicoDirectoObjectoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoReconhecimentoFisicoDirectoObjectoDTO autoReconhecimentoFisicoDirectoObjectoToAutoReconhecimentoFisicoDirectoObjectoDTO(AutoReconhecimentoFisicoDirectoObjecto entity);
}
