package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.EstadoProcesso;
import ao.gov.sic.sip.enums.TipoProcesso;
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
@Table(name = "processos")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE processos SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Processo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(unique = true)
    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoProcesso tipoProcesso;

    private Integer ano;

    @ManyToMany
    @JoinTable(
            name = "processos_crimes",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "crime_id")
    )
    @Builder.Default
    private Set<TipoCrime> crimes = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "processos_arguidos",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "arguido_id")
    )
    @Builder.Default
    private Set<Arguido> arguidos = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "processos_diligencias",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "diligencia_id")
    )
    private Set<Diligencia> diligencias;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "processos_advogados",
            joinColumns = @JoinColumn(name = "processo_id"),
            inverseJoinColumns = @JoinColumn(name = "advogado_id")
    )
    private Set<ProcessoAdvogado> advogados;

    @ManyToOne
    @JoinColumn(name = "queixoso_id")
    private Queixoso queixoso;

    @ManyToOne
    @JoinColumn(name = "magistrado_id")
    Magistrado magistrado;

    @ManyToOne
    @JoinColumn(name = "instrutor_id")
    private Instrutor instrutor;

    @ManyToOne
    @JoinColumn(name = "direcao_id")
    private Direcao direcao;

    @ManyToOne
    @JoinColumn(name = "secretaria_id")
    private Secretaria secretaria;

    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private EstadoProcesso estadoProcesso;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
