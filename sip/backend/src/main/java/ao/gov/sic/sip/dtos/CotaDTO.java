package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CotaDTO {
    private Long id;
    private LocalDate dataEmissao;
    private String materiaAutos;
    private String termoJuntada;
    private Long processoId;
    private Long userId;
}
