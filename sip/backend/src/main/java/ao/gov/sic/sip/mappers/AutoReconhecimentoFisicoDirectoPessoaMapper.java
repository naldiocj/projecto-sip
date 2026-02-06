package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoPessoaDTO;
import ao.gov.sic.sip.entities.AutoReconhecimentoFisicoDirectoPessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoReconhecimentoFisicoDirectoPessoaMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoReconhecimentoFisicoDirectoPessoa autoReconhecimentoFisicoDirectoPessoaDTOToAutoReconhecimentoFisicoDirectoPessoa(AutoReconhecimentoFisicoDirectoPessoaDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoReconhecimentoFisicoDirectoPessoaDTO autoReconhecimentoFisicoDirectoPessoaToAutoReconhecimentoFisicoDirectoPessoaDTO(AutoReconhecimentoFisicoDirectoPessoa entity);
}
