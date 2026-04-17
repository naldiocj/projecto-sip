package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.EstadoProcesso;
import ao.gov.sic.sip.enums.TipoProcesso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcessoDetailDTO {
    private Long id;
    private String nome;
    private String descricao;
    private String numero;
    private TipoProcesso tipoProcesso;

    private EstadoProcesso estadoProcesso;

    private Integer ano;
    private Set<CrimeResDTO> crimes;
    private Set<Long> arguidosIds; // TODO change to new DTO
    private Long queixosoId;
    private Long magistradoId;
    private Long instrutorId;
    private Long direcaoId;
    private Long secretariaId;
    private Long userId;
    private LocalDateTime createdAt;
}
