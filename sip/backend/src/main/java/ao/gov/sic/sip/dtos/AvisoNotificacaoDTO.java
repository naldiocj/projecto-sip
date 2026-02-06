package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.NaQualidade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvisoNotificacaoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long arguidoId;
    private Long enderecoDestinoId;
    private NaQualidade naQualidadeDe;
    private LocalDate dataComparencia;
    private Boolean vistoDirector;
    private Long processoId;
    private Long userId;
}
