package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.records.MagistradoCSVRecord;
import ao.gov.sic.sip.services.MagistradoCSVService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class MagistradoCSVServiceImpl implements MagistradoCSVService {
    @Override
    public List<MagistradoCSVRecord> convertCSV(File csvFile) {
        try (FileReader reader = new FileReader(csvFile)) {

            return new CsvToBeanBuilder<MagistradoCSVRecord>(reader)
                    .withType(MagistradoCSVRecord.class)
                    .build().parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
