package ao.gov.sic.sip.services;

import ao.gov.sic.sip.records.InstrutorCSVRecord;

import java.io.File;
import java.util.List;

public interface InstrutorCSVService {
    List<InstrutorCSVRecord> convertCSV(File csvFile);
}
