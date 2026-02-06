package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.records.TipoCrimeCSVRecord;
import ao.gov.sic.sip.services.TipoCrimeCSVService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class TipoCrimeCSVServiceImpl implements TipoCrimeCSVService {
    @Override
    public List<TipoCrimeCSVRecord> convertCSV(File csvFile) {
        try (FileReader reader = new FileReader(csvFile)) {

            return new CsvToBeanBuilder<TipoCrimeCSVRecord>(reader)
                    .withType(TipoCrimeCSVRecord.class)
                    .build().parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
