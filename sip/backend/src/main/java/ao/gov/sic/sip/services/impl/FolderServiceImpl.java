package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.services.FolderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FolderServiceImpl implements FolderService {
    private final Path fileStorageLocation;

    public FolderServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório de upload.", ex);
        }
    }
    @Override
    public Path create(String dirName) throws IOException {
        Path path = Paths.get(fileStorageLocation + "/" + dirName);

        return Files.createDirectories(path);
    }
}
