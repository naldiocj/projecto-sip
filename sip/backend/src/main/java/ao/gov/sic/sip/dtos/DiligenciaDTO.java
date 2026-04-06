package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.EstadoDiligencia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiligenciaDTO {
    private Long id;
    private Integer ordem;
    private String etapa;
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private EstadoDiligencia estado;
    private Long processoId;
    private String processoNumero;
    private Long userId;
}
