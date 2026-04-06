package ao.gov.sic.sip.dtos;

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
public class AutoQueixaDTO {
    private Long id;
    private LocalDate dataEmissao;
    private String materiaAutos;
    private List<Long> arguidosQueixasIds;
    private List<Long> testemunhaQueixasIds;
    private Long processoId;
    private Long userId;
}
