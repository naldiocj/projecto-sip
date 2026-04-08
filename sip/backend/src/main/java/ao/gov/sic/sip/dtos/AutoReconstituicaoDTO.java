package ao.gov.sic.sip.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoReconstituicaoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String meiosUtilizados;
    private String descricao;
    private Long processoId;
    private Long userId;
}
