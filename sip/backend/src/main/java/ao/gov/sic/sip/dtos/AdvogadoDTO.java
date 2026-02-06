package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.TipoAdvogado;
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
    private TipoAdvogado tipoAdvogado;
    private Long processoId;
    private Long userId;
}
