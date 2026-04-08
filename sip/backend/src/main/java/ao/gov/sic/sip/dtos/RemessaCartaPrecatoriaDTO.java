package ao.gov.sic.sip.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RemessaCartaPrecatoriaDTO {
    private Long id;
    private String numeroOficio;
    private String numeroCarta;
    private String assunto;
    private LocalDate dataEmissao;
    private String descricao;
    private Long processoId;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
