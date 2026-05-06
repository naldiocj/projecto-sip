package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ProcessoDTO;
import ao.gov.sic.sip.dtos.ProcessoDetailDTO;
import ao.gov.sic.sip.dtos.ProcessoResDTO;
import ao.gov.sic.sip.entities.Arguido;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.TipoCrime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {TipoCrimeMapper.class, DirecaoMapper.class, SecretariaMapper.class, ArguidoMapper.class, QueixosoMapper.class, MagistradoMapper.class, InstrutorMapper.class, UserMapper.class, ItemMapper.class}, componentModel = "spring")
public interface ProcessoMapper {
    @Mapping(source = "queixosoId", target = "queixoso.id")
    @Mapping(source = "magistradoId", target = "magistrado.id")
    @Mapping(source = "instrutorId", target = "instrutor.id")
    @Mapping(source = "direcaoId", target = "direcao.id")
    @Mapping(source = "secretariaId", target = "secretaria.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "crimes", ignore = true)
    @Mapping(target = "arguidos", ignore = true)
    Processo processoDTOToProcesso(ProcessoDTO dto);

    @Mapping(source = "queixoso.id", target = "queixosoId")
    @Mapping(source = "magistrado.id", target = "magistradoId")
    @Mapping(source = "instrutor.id", target = "instrutorId")
    @Mapping(source = "direcao.id", target = "direcaoId")
    @Mapping(source = "secretaria.id", target = "secretariaId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "crimes", target = "crimesIds")
    @Mapping(source = "arguidos", target = "arguidosIds")
    ProcessoDTO processoToProcessoDTO(Processo entity);

    @Mapping(source = "queixoso.id", target = "queixosoId")
    @Mapping(source = "magistrado.id", target = "magistradoId")
    @Mapping(source = "instrutor.id", target = "instrutorId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "crimes", target = "crimes")
    @Mapping(source = "arguidos", target = "arguidosIds")
    ProcessoDetailDTO processoToProcessoDetailDTO(Processo entity);

    @Mapping(source = "estadoProcesso", target = "estadoProcesso")
    ProcessoResDTO processoToProcessoResDTO(Processo entity);


    default Set<Long> mapCrimesToIds(Set<TipoCrime> crimes) {
        if (crimes == null) {
            return null;
        }
        return crimes.stream().map(TipoCrime::getId).collect(Collectors.toSet());
    }

    default Set<Long> mapArguidosToIds(Set<Arguido> arguidos) {
        if (arguidos == null) {
            return null;
        }
        return arguidos.stream().map(Arguido::getId).collect(Collectors.toSet());
    }
}
