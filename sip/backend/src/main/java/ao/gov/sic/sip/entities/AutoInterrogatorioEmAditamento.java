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
@Table(name = "autos_interrogatorios_em_aditamentos")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE autos_interrogatorios_em_aditamentos SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class AutoInterrogatorioEmAditamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numeroFolha;

    private LocalDate dataEmissao;

    @ManyToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    private String defensorOficioso;

    @ManyToOne
    @JoinColumn(name = "processo_id")
    private Processo processo;

    @Column(columnDefinition = "TEXT")
    private String materiaAutos;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
