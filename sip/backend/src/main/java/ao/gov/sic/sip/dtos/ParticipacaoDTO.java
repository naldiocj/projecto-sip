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
public class ParticipacaoDTO {
    private Long id;
    private LocalDate dataEmissao;
    private String participante;
    private Long queixosoId;
    private String materiaAutos;
    private String parteApresentacao;
    private String autoApreensaoTexto;
    private Long autoApreensaoId;
    private Long processoId;
    private Long userId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String arquivo;
}
