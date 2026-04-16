package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ProcessoDocumentoDTO;
import ao.gov.sic.sip.dtos.ProcessoDocumentoItemDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.ProcessoDocumento;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.BadRequestException;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.records.FileRecord;
import ao.gov.sic.sip.repositories.ProcessoDocumentoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.services.ProcessoDocumentoService;
import ao.gov.sic.sip.services.ProcessoService;
import ao.gov.sic.sip.services.StorageFileService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessoDocumentoServiceImpl implements ProcessoDocumentoService {
    private final ProcessoService processoService;
    private final ProcessoRepository processoRepository;
    private final StorageFileService storageFileService;
    private final ProcessoDocumentoRepository processoDocumentoRepository;
    private final UserService userService;

    @Override
    public Response<?> saveDocumento(ProcessoDocumentoDTO dto) {
        Processo processo = processoRepository.findFirstByNumero(dto.getProcessoNumero());

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        User user = userService.currentUser();

        try {
            FileRecord fileRecord = storageFileService.saveToFolder(dto.getArquivo(), "Documentos");
            processoDocumentoRepository.save(
                    ProcessoDocumento.builder()
                            .processo(processo)
                            .arquivo(fileRecord.getFileName())
                            .tipo(dto.getTipo())
                            .user(user)
                            .createdAt(LocalDateTime.now())
                            .titulo(dto.getTitulo())
                            .descricao(dto.getDescricao())
                            .build()
            );
        } catch (Exception e) {
            throw new BadRequestException("Erro ao salvar o documento");
        }

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Documento salvo com sucesso")
                .build();
    }

    @Override
    public Response<List<ProcessoDocumentoItemDTO>> getDocumentosByProcessoId(Long id) {
        List<ProcessoDocumentoItemDTO> documentoItemDTOS = processoDocumentoRepository.findAll().stream()
                .filter(documento -> documento
                        .getProcesso().getId().equals(id))
                .map(documento -> ProcessoDocumentoItemDTO.builder()
                        .processoNumero(documento.getProcesso().getNumero())
                        .id(documento.getId())
                        .url(documento.getArquivo())
                        .tipo(documento.getTipo())
                        .descricao(documento.getDescricao())
                        .titulo(documento.getTitulo())
                        .build())
                .toList();

        return Response.<List<ProcessoDocumentoItemDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(documentoItemDTOS.isEmpty() ? "Nenhum documento encontrado" : "Documentos encontrados")
                .data(documentoItemDTOS)
                .build();
    }
}
