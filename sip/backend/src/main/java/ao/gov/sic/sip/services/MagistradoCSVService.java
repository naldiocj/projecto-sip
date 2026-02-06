package ao.gov.sic.sip.services;

import ao.gov.sic.sip.records.MagistradoCSVRecord;

import java.io.File;
import java.util.List;

public interface MagistradoCSVService {
    List<MagistradoCSVRecord> convertCSV(File csvFile);
}
