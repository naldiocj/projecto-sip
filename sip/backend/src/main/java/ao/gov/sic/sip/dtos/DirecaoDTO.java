package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DirecaoDTO {
    private Long id;

    private String nome;
    private String sigla;
    private String descricao;
    private Long userId;
}
