package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoReconhecimentoDescricaoPessoaDTO;
import ao.gov.sic.sip.entities.AutoReconhecimentoDescricaoPessoa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoReconhecimentoDescricaoPessoaMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoReconhecimentoDescricaoPessoa autoReconhecimentoDescricaoPessoaDTOToAutoReconhecimentoDescricaoPessoa(AutoReconhecimentoDescricaoPessoaDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoReconhecimentoDescricaoPessoaDTO autoReconhecimentoDescricaoPessoaToAutoReconhecimentoDescricaoPessoaDTO(AutoReconhecimentoDescricaoPessoa entity);
}
