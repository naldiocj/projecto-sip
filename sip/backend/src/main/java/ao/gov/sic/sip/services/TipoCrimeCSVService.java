package ao.gov.sic.sip.services;

import ao.gov.sic.sip.records.TipoCrimeCSVRecord;

import java.io.File;
import java.util.List;

public interface TipoCrimeCSVService {
    List<TipoCrimeCSVRecord> convertCSV(File csvFile);
}
