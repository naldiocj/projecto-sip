package ao.gov.sic.sip.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PatenteDTO {
    private Long id;

    @NotEmpty(message = "O campo nome é obrigatório")
    private String nome;

    private CategoriaDTO categoria;
}
