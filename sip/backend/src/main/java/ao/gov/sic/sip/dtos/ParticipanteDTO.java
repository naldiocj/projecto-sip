package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.TipoParticipante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteDTO {
    private Long id;
    private Long queixosoId;
    private Long advogadoId;
    private Long arguidoId;
    private Long testemunhaId;
    private TipoParticipante tipoParticipante;
    private Long processoId;
}

