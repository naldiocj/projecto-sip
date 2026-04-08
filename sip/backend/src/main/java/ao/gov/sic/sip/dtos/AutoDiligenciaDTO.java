package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoDiligenciaDTO {
    private Long id;
    private String introducao;
    private String desenvolvimento;
    private String conclusao;
    private Long processoId;
    private Long userId;
}
