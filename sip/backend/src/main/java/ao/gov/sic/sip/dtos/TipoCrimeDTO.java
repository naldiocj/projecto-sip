package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoCrimeDTO {
    private Long id;
    private String artigoPenal;
    private String descricao;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long userId;
}
