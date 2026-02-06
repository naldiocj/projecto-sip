package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.PedidoComparenciaDTO;
import ao.gov.sic.sip.entities.PedidoComparencia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PedidoComparenciaMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "arguidoId", target = "arguido.id")
    @Mapping(source = "testemunhaId", target = "testemunha.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    PedidoComparencia pedidoComparenciaDTOToPedidoComparencia(PedidoComparenciaDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "arguido.id", target = "arguidoId")
    @Mapping(source = "testemunha.id", target = "testemunhaId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    PedidoComparenciaDTO pedidoComparenciaToPedidoComparenciaDTO(PedidoComparencia entity);
}
