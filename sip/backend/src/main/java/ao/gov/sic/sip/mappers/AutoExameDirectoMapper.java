package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoExameDirectoDTO;
import ao.gov.sic.sip.entities.AutoExameDirecto;
import ao.gov.sic.sip.entities.PeritoExameDirecto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AutoExameDirectoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "peritoExameDirectos", ignore = true)
    AutoExameDirecto autoExameDirectoDTOToAutoExameDirecto(AutoExameDirectoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "peritoExameDirectos", target = "peritoExameDirectosIds")
    AutoExameDirectoDTO autoExameDirectoToAutoExameDirectoDTO(AutoExameDirecto entity);

    default List<Long> mapPeritosToIds(List<PeritoExameDirecto> peritos) {
        if (peritos == null) {
            return null;
        }
        return peritos.stream().map(PeritoExameDirecto::getId).collect(Collectors.toList());
    }
}
