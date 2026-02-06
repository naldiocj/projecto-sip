package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
public class StorageFileServiceImpl implements StorageFileService {
    private final Path fileStorageLocation;

    public StorageFileServiceImpl(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório de upload.", ex);
        }
    }

    @Override
    public String generateUniqueName(MultipartFile file) {
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String newName = UUID.randomUUID().toString();
        return (extension != null) ? newName + "." + extension : newName;
    }

    @Override
    public FileRecord save(MultipartFile csvFile) {
        String fileName = generateUniqueName(csvFile);
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Nome de arquivo inválido.");
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(csvFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return new FileRecord(new File(targetLocation.toString()), fileName);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar arquivo: " + fileName, e);
        }
    }

    @Override
    public Boolean remove(String fileName) {
        try {
            Path path = this.fileStorageLocation.resolve(fileName);

            if (Files.deleteIfExists(path)) {
                log.info("Arquivo deletado com sucesso: {}", fileName);
                return true;
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao deletar arquivo: " + fileName, e);
        }

    }
}
