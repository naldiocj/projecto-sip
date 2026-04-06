package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoCorpoDelitoIndirectoDTO;
import ao.gov.sic.sip.entities.AutoCorpoDelitoIndirecto;
import ao.gov.sic.sip.entities.Testemunha;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AutoCorpoDelitoIndirectoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "testemunhas", ignore = true)
    AutoCorpoDelitoIndirecto autoCorpoDelitoIndirectoDTOToAutoCorpoDelitoIndirecto(AutoCorpoDelitoIndirectoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "testemunhas", target = "testemunhasIds")
    AutoCorpoDelitoIndirectoDTO autoCorpoDelitoIndirectoToAutoCorpoDelitoIndirectoDTO(AutoCorpoDelitoIndirecto entity);

    default List<Long> mapTestemunhasToIds(List<Testemunha> testemunhas) {
        if (testemunhas == null) {
            return null;
        }
        return testemunhas.stream().map(Testemunha::getId).collect(Collectors.toList());
    }
}
