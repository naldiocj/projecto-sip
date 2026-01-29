package ao.gov.sic.sip.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;
}
