package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.records.DirecaoCSVRecord;
import ao.gov.sic.sip.services.DirecaoCSVService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class DirecaoCSVServiceImpl implements DirecaoCSVService {
    @Override
    public List<DirecaoCSVRecord> convertCSV(File csvFile) {
        try (FileReader reader = new FileReader(csvFile)) {

            return new CsvToBeanBuilder<DirecaoCSVRecord>(reader)
                    .withType(DirecaoCSVRecord.class)
                    .build().parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
