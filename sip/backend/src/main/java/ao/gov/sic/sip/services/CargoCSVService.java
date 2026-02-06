package ao.gov.sic.sip.services;

import ao.gov.sic.sip.records.CargoCSVRecord;

import java.io.File;
import java.util.List;

public interface CargoCSVService {
    List<CargoCSVRecord> convertCSV(File csvFile);
}
