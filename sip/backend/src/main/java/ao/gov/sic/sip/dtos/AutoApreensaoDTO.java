package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.enums.NaQualidade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoApreensaoDTO {
    private Long id;
    private Integer numeroFolha;
    private LocalDate dataEmissao;
    private Long enderecoId;
    private NaQualidade naQualidadeDe;
    private String naturezaCaracteristicas;
    private String materiaAutos;
    private List<Long> peritosApreensoesIds;
    private List<Long> objectoApreensoesIds;
    private Long processoId;
    private Long userId;
}
