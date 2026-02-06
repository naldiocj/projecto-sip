package ao.gov.sic.sip.records;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MagistradoCSVRecord {
    @CsvBindByName(column = "nome")
    private String nome;
}
