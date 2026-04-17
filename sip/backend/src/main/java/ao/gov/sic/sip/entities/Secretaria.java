package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.SecretariaType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "secretarias")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE secretarias SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Secretaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    @ManyToOne
    @JoinColumn(name = "patente_id")
    private Patente patente;

    @Enumerated(EnumType.STRING)
    private SecretariaType type;

    @ManyToOne
    @JoinColumn(name = "direcao_id")
    private Direcao direcao;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
