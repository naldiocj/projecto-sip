package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.NaQualidade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoCorpoDelitoIndirectoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private NaQualidade naQualidadeDe;
    private String naturezaCaracteristicas;
    private String materiaAutos;
    private List<Long> testemunhasIds;
    private Long processoId;
    private Long userId;
}
