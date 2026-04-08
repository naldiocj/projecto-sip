package ao.gov.sic.sip.mappers;

import ao.gov.sic.sip.dtos.ParticipanteDTO;
import ao.gov.sic.sip.dtos.ParticipanteResDTO;
import ao.gov.sic.sip.entities.Participante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {QueixosoMapper.class, AdvogadoMapper.class, ArguidoMapper.class, TestemunhaMapper.class, ProcessoMapper.class})
public interface ParticipanteMapper {
    @Mapping(source = "queixosoId", target = "queixoso.id")
    @Mapping(source = "advogadoId", target = "advogado.id")
    @Mapping(source = "arguidoId", target = "arguido.id")
    @Mapping(source = "testemunhaId", target = "testemunha.id")
    @Mapping(source = "processoId", target = "processo.id")
    Participante participanteDTOToParticipante(ParticipanteDTO dto);

    @Mapping(source = "queixoso.id", target = "queixosoId")
    @Mapping(source = "advogado.id", target = "advogadoId")
    @Mapping(source = "arguido.id", target = "arguidoId")
    @Mapping(source = "testemunha.id", target = "testemunhaId")
    @Mapping(source = "processo.id", target = "processoId")
    ParticipanteDTO participanteToParticipanteDTO(Participante entity);


    @Mapping(source = "queixoso.id", target = "queixosoId")
    @Mapping(source = "advogado.id", target = "advogadoId")
    @Mapping(source = "arguido.id", target = "arguidoId")
    @Mapping(source = "testemunha.id", target = "testemunhaId")
    @Mapping(source = "processo.id", target = "processoId")
    @Mapping(source = "queixoso", target = "queixoso")
    @Mapping(source = "arguido", target = "arguido")
    @Mapping(source = "testemunha", target = "testemunha")
    @Mapping(source = "advogado", target = "advogado")
    ParticipanteResDTO participanteToParticipanteResDTO(Participante entity);
}
