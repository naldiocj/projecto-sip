package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.NaQualidade;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autos_apreensoes")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE autos_apreensoes SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class AutoApreensao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroFolha;

    private LocalDate dataEmissao;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Enumerated(EnumType.STRING)
    private NaQualidade naQualidadeDe;

    @Column(columnDefinition = "TEXT")
    private String naturezaCaracteristicas;

    @Column(columnDefinition = "TEXT")
    private String materiaAutos;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autos_aprensoes_participantes",
            joinColumns = @JoinColumn(name = "auto_apreensao_id"),
            inverseJoinColumns = @JoinColumn(name = "participante_id")
    )
    @Builder.Default
    private List<ParticipanteAutoApreensao> peritosApreensoes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "autos_aprensoes_objectos",
            joinColumns = @JoinColumn(name = "auto_apreensao_id"),
            inverseJoinColumns = @JoinColumn(name = "objecto_id")
    )
    @Builder.Default
    private List<ObjectoAutoApreensao> objectoApreensoes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

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
