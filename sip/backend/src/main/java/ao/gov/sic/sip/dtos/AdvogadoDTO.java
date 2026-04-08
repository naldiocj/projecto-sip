package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.TipoAdvogado;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdvogadoDTO {
    private Long id;
    private String nomeCompleto;
    private String numeroCedula;
    private String telefone;
    private TipoAdvogado tipoAdvogado;
    private Long processoId;
    private String processoNumero;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
