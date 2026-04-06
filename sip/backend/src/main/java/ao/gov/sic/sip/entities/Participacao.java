package ao.gov.sic.sip.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "participacoes")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE participacoes SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Participacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEmissao;

    private String participante;

    @ManyToOne
    @JoinColumn(name = "queixoso_id")
    private Queixoso queixoso;

    @Column(columnDefinition = "TEXT")
    private String materiaAutos;

    @Column(columnDefinition = "TEXT")
    private String parteApresentacao;

    @Column(columnDefinition = "TEXT")
    private String autoApreensaoTexto;

    @ManyToOne
    @JoinColumn(name = "auto_apreensao_id")
    private AutoApreensao autoApreensao;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    private String arquivo;

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
