package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.RemessaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Documento;
import ao.gov.sic.sip.entities.Remessa;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.RemessaMapper;
import ao.gov.sic.sip.repositories.DocumentoRepository;
import ao.gov.sic.sip.repositories.RemessaRepository;
import ao.gov.sic.sip.services.RemessaService;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RemessaServiceImpl implements RemessaService {

    private final RemessaRepository remessaRepository;
    private final RemessaMapper remessaMapper;
    private final DocumentoRepository documentoRepository;
    private final StorageFileService storageFileService;

    @Override
    @Transactional
    public Response<RemessaDTO> create(RemessaDTO dto) {
        Remessa remessa = remessaMapper.toEntity(dto);

        if (dto.getDocumentosIds() != null && !dto.getDocumentosIds().isEmpty()) {
            List<Documento> documentos = documentoRepository.findAllById(dto.getDocumentosIds());
            remessa.setDocumentos(new HashSet<>(documentos));
        }

        if (dto.getArquivo() != null && !dto.getArquivo().isEmpty()) {
            String fileName = storageFileService.saveToFolder(dto.getArquivo(), "Remessas")
                    .getFileName();
            remessa.setArquivo(fileName);
        }

        remessa = remessaRepository.save(remessa);
        
        return Response.<RemessaDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Remessa criada com sucesso")
                .data(remessaMapper.toDTO(remessa))
                .build();
    }

    @Override
    @Transactional
    public Response<RemessaDTO> update(Long id, RemessaDTO dto) {
        Remessa remessa = remessaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Remessa não encontrada"));

        remessa.setCodigoRastreio(dto.getCodigoRastreio());
        remessa.setOrigem(dto.getOrigem());
        remessa.setDestino(dto.getDestino());
        remessa.setDataEnvio(dto.getDataEnvio());
        remessa.setDataRecebimento(dto.getDataRecebimento());
        remessa.setStatus(dto.getStatus());
        remessa.setResponsavelEnvio(dto.getResponsavelEnvio());

        if (dto.getDocumentosIds() != null) {
            List<Documento> documentos = documentoRepository.findAllById(dto.getDocumentosIds());
            remessa.setDocumentos(new HashSet<>(documentos));
        }

        if (dto.getArquivo() != null && !dto.getArquivo().isEmpty()) {
            String fileName = storageFileService.saveToFolder(dto.getArquivo(), "Remessas")
                    .getFileName();
            remessa.setArquivo(fileName);
        }

        remessa = remessaRepository.save(remessa);
        
        return Response.<RemessaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Remessa actualizada com sucesso")
                .data(remessaMapper.toDTO(remessa))
                .build();
    }

    @Override
    public Response<RemessaDTO> findById(Long id) {
        RemessaDTO dto = remessaRepository.findById(id)
                .map(remessaMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Remessa não encontrada"));

        return Response.<RemessaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Remessa encontrada")
                .data(dto)
                .build();
    }

    @Override
    public Response<List<RemessaDTO>> findAll() {
        List<RemessaDTO> dtos = remessaRepository.findAll().stream()
                .map(remessaMapper::toDTO)
                .collect(Collectors.toList());

        return Response.<List<RemessaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(dtos.isEmpty() ? "Nenhuma remessa encontrada" : "Remessas encontradas")
                .data(dtos)
                .build();
    }

    @Override
    @Transactional
    public Response<Void> delete(Long id) {
        if (!remessaRepository.existsById(id)) {
            throw new NotFoundException("Remessa não encontrada");
        }
        remessaRepository.deleteById(id);

        return Response.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Remessa eliminada com sucesso")
                .build();
    }
}
