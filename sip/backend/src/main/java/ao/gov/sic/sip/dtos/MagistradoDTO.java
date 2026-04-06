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
public class MagistradoDTO {
    private Long id;
    private String nomeCompleto;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
