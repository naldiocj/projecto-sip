package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDTO {
    private Long id;
    private String nomeCompleto;
    private Long patenteId;
    private Long cargoId;
    private Long direcaoId;
    private Long userId;
}
