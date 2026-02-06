package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartaPrecatoriaDTO {
    private Long id;
    private String numeroCarta;
    private LocalDate dataEmissao;
    private String deprecante;
    private Long enderecoId;
    private String descricao;
    private Long livroRegistoId;
    private Long processoId;
    private Long userId;
}
