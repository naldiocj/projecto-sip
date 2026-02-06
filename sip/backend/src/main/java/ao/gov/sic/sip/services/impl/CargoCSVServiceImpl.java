package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.records.CargoCSVRecord;
import ao.gov.sic.sip.services.CargoCSVService;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class CargoCSVServiceImpl implements CargoCSVService {
    @Override
    public List<CargoCSVRecord> convertCSV(File csvFile) {
        try (FileReader reader = new FileReader(csvFile)) {

            return new CsvToBeanBuilder<CargoCSVRecord>(reader)
                    .withType(CargoCSVRecord.class)
                    .build().parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
