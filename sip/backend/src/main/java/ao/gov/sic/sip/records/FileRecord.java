package ao.gov.sic.sip.records;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileRecord {
    private File file;
    private String fileName;
}
