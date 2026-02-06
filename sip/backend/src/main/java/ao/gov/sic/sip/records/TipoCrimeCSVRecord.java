package ao.gov.sic.sip.records;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoCrimeCSVRecord {
    @CsvBindByName(column = "artigoPenal")
    private String artigoPenal;

    @CsvBindByName(column = "descricao")
    private String descricao;
}
