package ao.gov.sic.sip.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstrutorDTO {
    private Long id;
    private String nomeCompleto;
    private Long patenteId;
    private Long cargoId;
    private Long direcaoId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
