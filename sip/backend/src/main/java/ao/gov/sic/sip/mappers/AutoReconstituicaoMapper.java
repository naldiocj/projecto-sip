package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoReconstituicaoDTO;
import ao.gov.sic.sip.entities.AutoReconstituicao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AutoReconstituicaoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    AutoReconstituicao autoReconstituicaoDTOToAutoReconstituicao(AutoReconstituicaoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    AutoReconstituicaoDTO autoReconstituicaoToAutoReconstituicaoDTO(AutoReconstituicao entity);
}
