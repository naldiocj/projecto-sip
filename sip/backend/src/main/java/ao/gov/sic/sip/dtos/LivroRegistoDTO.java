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
public class LivroRegistoDTO {
    private Long id;
    private String numeroLivro;
    private Integer numeroFolha;
    private LocalDate dataOcorrencia;
    private String descricao;
    private Long userId;
}
