package ao.gov.sic.sip.services;

import ao.gov.sic.sip.dtos.DocumentoDTO;

import java.util.List;

public interface DocumentoService {
    DocumentoDTO create(DocumentoDTO dto);
    DocumentoDTO update(Long id, DocumentoDTO dto);
    DocumentoDTO findById(Long id);
    List<DocumentoDTO> findAll();
    void delete(Long id);
}