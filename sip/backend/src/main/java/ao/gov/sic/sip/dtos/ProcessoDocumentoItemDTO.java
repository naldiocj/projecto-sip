package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDocumentoItemDTO {
    private Long id;

    private String titulo;
    private String tipo;
    private String descricao;
    private String url;
    private String processoNumero;
}
