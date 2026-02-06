package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.entities.Role;
import ao.gov.sic.sip.enums.AuthMethod;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Boolean emailVerified;
    private AuthMethod provider;
    private String providerId;
    private List<Role> roles;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
