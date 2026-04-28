package ao.gov.sic.sip.entities;

import ao.gov.sic.sip.enums.EstadoDetencao;
import ao.gov.sic.sip.enums.Sexo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "detidos")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE detidos SET is_deleted = true WHERE id=?")
@SQLRestriction("is_deleted=false")
public class Detido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroProcesso;

    @Column(nullable = false)
    private String nomeCompleto;

    private String nomePai;
    private String nomeMae;
    private LocalDate dataNascimento;
    private String naturalidade;
    private String profissao;
    private String numeroBi;
    private LocalDate dataEmissaoBi;
    private String localEmissaoBi;
    private String telefone;
    private String endereco;
    private String estadoCivil;
    private Integer idade;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private EstadoDetencao estadoDetencao;

    private LocalDateTime dataDetencao;
    private String localDetencao;

    @Column(columnDefinition = "TEXT")
    private String motivoDetencao;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
