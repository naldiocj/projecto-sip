package ao.gov.sic.sip.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCrimeDTO {
    private Long id;
    private String artigoPenal;
    private String descricao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
