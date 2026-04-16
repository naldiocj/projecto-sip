package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dto.DocumentoDTO;
import ao.gov.sic.sip.entities.Documento;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.mappers.DocumentoMapper;
import ao.gov.sic.sip.repositories.DocumentoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.DocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;
    private final UserRepository userRepository;
    private final DocumentoMapper mapper;

    @Override
    @Transactional
    public DocumentoDTO create(DocumentoDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Documento entity = mapper.toEntity(dto);
        entity.setUser(user);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional
    public DocumentoDTO update(Long id, DocumentoDTO dto) {
        Documento existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));

        if (dto.getUserId() != null && !dto.getUserId().equals(existing.getUser().getId())) {
            User newUser = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("Novo usuário não encontrado"));
            existing.setUser(newUser);
        }

        existing.setNumeroProcesso(dto.getNumeroProcesso());
        existing.setTipoModelo(dto.getTipoModelo());
        existing.setConteudo(dto.getConteudo());

        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentoDTO findById(Long id) {
        return repository.findById(id).map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentoDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) { repository.deleteById(id); }
}