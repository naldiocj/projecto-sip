package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoExameDirectoAvaliacaoDTO;
import ao.gov.sic.sip.entities.AutoExameDirectoAvaliacao;
import ao.gov.sic.sip.entities.PeritoExameDirectoAvaliacao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AutoExameDirectoAvaliacaoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "peritoExameDirectosAvaliacoes", ignore = true)
    AutoExameDirectoAvaliacao autoExameDirectoAvaliacaoDTOToAutoExameDirectoAvaliacao(AutoExameDirectoAvaliacaoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "peritoExameDirectosAvaliacoes", target = "peritoExameDirectosAvaliacoesIds")
    AutoExameDirectoAvaliacaoDTO autoExameDirectoAvaliacaoToAutoExameDirectoAvaliacaoDTO(AutoExameDirectoAvaliacao entity);

    default List<Long> mapPeritosAvaliacoesToIds(List<PeritoExameDirectoAvaliacao> peritos) {
        if (peritos == null) {
            return null;
        }
        return peritos.stream().map(PeritoExameDirectoAvaliacao::getId).collect(Collectors.toList());
    }
}
