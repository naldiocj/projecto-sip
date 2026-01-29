package ao.gov.sic.sip.dtos;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long id;

    private String nome;
}
