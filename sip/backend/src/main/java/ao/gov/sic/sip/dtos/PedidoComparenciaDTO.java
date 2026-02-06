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
public class PedidoComparenciaDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataComparencia;
    private NaQualidade naQualidadeDe;
    private String assunto;
    private Long enderecoId;
    private Long arguidoId;
    private Long testemunhaId;
    private Long processoId;
    private String materiaAutos;
    private Long userId;
}
