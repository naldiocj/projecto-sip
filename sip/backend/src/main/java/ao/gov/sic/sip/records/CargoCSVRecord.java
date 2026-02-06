package ao.gov.sic.sip.records;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoCSVRecord {
    @CsvBindByName(column = "nome")
    private String nome;

    @CsvBindByName(column = "sigla")
    private String sigla;

    @CsvBindByName(column = "descricao")
    private String descricao;
}
