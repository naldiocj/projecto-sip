package ao.gov.sic.sip.utils;

import ao.gov.sic.sip.entities.Processo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ProcessoSpecifications {
    public static Specification<Processo> hasTerm(String term) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(term)) return null;

            // This creates: WHERE LOWER(nome) LIKE %term% OR LOWER(numero) LIKE %term%
            String pattern = "%" + term.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("descricao")), pattern),
                    cb.like(cb.lower(root.get("numero")), pattern)

            );
        };
    }
}