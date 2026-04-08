package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.AutoApreensaoDTO;
import ao.gov.sic.sip.entities.AutoApreensao;
import ao.gov.sic.sip.entities.ObjectoAutoApreensao;
import ao.gov.sic.sip.entities.ParticipanteAutoApreensao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface AutoApreensaoMapper {
    @Mapping(source = "enderecoId", target = "endereco.id")
    @Mapping(source = "processoId", target = "processo.id")
    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "peritosApreensoes", ignore = true)
    @Mapping(target = "objectoApreensoes", ignore = true)
    AutoApreensao autoApreensaoDTOToAutoApreensao(AutoApreensaoDTO dto);

    @Mapping(source = "endereco.id", target = "enderecoId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "peritosApreensoes", target = "peritosApreensoesIds")
    @Mapping(source = "objectoApreensoes", target = "objectoApreensoesIds")
    AutoApreensaoDTO autoApreensaoToAutoApreensaoDTO(AutoApreensao entity);

    default List<Long> mapParticipantesToIds(List<ParticipanteAutoApreensao> participantes) {
        if (participantes == null) {
            return null;
        }
        return participantes.stream().map(ParticipanteAutoApreensao::getId).collect(Collectors.toList());
    }

    default List<Long> mapObjectosToIds(List<ObjectoAutoApreensao> objectos) {
        if (objectos == null) {
            return null;
        }
        return objectos.stream().map(ObjectoAutoApreensao::getId).collect(Collectors.toList());
    }
}
