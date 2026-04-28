package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.StatusRemessa;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "remessas")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE remessas SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Remessa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String codigoRastreio;

    @Column(nullable = false)
    private String origem;

    @Column(nullable = false)
    private String destino;

    @Column(nullable = false)
    private LocalDateTime dataEnvio;

    private LocalDateTime dataRecebimento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusRemessa status;

    @ManyToMany
    @JoinTable(
            name = "remessas_documentos",
            joinColumns = @JoinColumn(name = "remessa_id"),
            inverseJoinColumns = @JoinColumn(name = "documento_id")
    )
    @Builder.Default
    private Set<Documento> documentos = new HashSet<>();

    @Column(nullable = false)
    private String responsavelEnvio;

    private String arquivo; // Assuming this is a path to a file

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
