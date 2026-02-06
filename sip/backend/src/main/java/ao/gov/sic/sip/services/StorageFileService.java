package ao.gov.sic.sip.services;

import ao.gov.sic.sip.records.FileRecord;
import org.springframework.web.multipart.MultipartFile;

public interface StorageFileService {

    FileRecord save(MultipartFile file);

    String generateUniqueName(MultipartFile file);

    Boolean remove(String fileName);
}
