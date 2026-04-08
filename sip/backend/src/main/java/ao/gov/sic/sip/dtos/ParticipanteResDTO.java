package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.entities.Advogado;
import ao.gov.sic.sip.entities.Arguido;
import ao.gov.sic.sip.entities.Queixoso;
import ao.gov.sic.sip.entities.Testemunha;
import ao.gov.sic.sip.enums.TipoParticipante;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipanteResDTO {
    private Long id;
    private Long queixosoId;
    private Queixoso queixoso;
    private Testemunha testemunha;
    private Advogado advogado;
    private Arguido arguido;
    private Long advogadoId;
    private Long arguidoId;
    private Long testemunhaId;
    private TipoParticipante tipoParticipante;
    private Long processoId;
}
