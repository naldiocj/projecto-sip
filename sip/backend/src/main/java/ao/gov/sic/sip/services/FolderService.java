package ao.gov.sic.sip.services;

import java.io.IOException;
import java.nio.file.Path;

public interface FolderService {
    Path create(String path) throws IOException;
}
