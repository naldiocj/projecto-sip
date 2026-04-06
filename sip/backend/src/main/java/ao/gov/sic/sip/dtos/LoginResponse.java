package ao.gov.sic.sip.dtos;

import ao.gov.sic.sip.entities.Action;
import ao.gov.sic.sip.enums.ActionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class LoginResponse {
    private String token;
    private List<String> roles;
    @Enumerated(EnumType.STRING)
    private Map<String, Set<ActionType>> actions;
}
