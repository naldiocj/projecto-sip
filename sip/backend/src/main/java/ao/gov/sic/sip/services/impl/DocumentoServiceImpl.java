package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.DocumentoDTO;
import ao.gov.sic.sip.entities.Documento;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.ProcessoDocumento;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DocumentoMapper;
import ao.gov.sic.sip.repositories.DocumentoRepository;
import ao.gov.sic.sip.repositories.ProcessoDocumentoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.DocumentoService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentoServiceImpl implements DocumentoService {

    private final DocumentoRepository repository;
    private final UserRepository userRepository;
    private final DocumentoMapper mapper;
    private final UserService userService;
    private final ProcessoDocumentoRepository processoDocumentoRepository;
    private final ProcessoRepository processoRepository;

    @Override
    @Transactional
    public DocumentoDTO create(DocumentoDTO dto) {

        User user = userService.currentUser();

        if (user == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Integer ultimaPagina = repository.findMaxPaginaByNumeroProcesso(dto.getNumeroProcesso());

        Documento entity = mapper.toEntity(dto);

        if (ultimaPagina == null) {
            entity.setPagina(1); // Primeira página do processo
        } else {
            entity.setPagina(ultimaPagina + 1);
        }
        
        entity.setUser(user);
        DocumentoDTO documentoDTO = mapper.toDTO(repository.save(entity));



        Processo processo = processoRepository.findFirstByNumero(dto.getNumeroProcesso());

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        processoDocumentoRepository.save(
                ProcessoDocumento.builder()
                        .processo(processo)
                        .documento(entity)
                        .arquivo(null)
                        .tipo(dto.getTipoModelo())
                        .user(user)
                        .createdAt(LocalDateTime.now())
                        .titulo(dto.getTipoModelo())
                        .descricao("Registo de " + dto.getTipoModelo())
                        .build());



        return documentoDTO;
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