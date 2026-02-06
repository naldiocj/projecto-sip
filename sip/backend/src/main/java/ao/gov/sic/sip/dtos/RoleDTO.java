package ao.gov.sic.sip.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDTO {
    private Long id;

    @NotEmpty(message = "{role.name.required}")
    private String name;
}
