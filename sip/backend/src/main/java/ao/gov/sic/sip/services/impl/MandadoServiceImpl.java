package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.MandadoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Mandado;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.MandadoMapper;
import ao.gov.sic.sip.repositories.MandadoRepository;
import ao.gov.sic.sip.repositories.UserRepository; // Import UserRepository
import ao.gov.sic.sip.services.MandadoService;
import ao.gov.sic.sip.services.StorageFileService;
import ao.gov.sic.sip.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MandadoServiceImpl implements MandadoService {

    private final MandadoRepository mandadoRepository;
    private final MandadoMapper mandadoMapper;
    private final StorageFileService storageFileService;
    private final UserService userService;
    private final UserRepository userRepository; // Inject UserRepository

    @Override
    @Transactional
    public Response<MandadoDTO> create(MandadoDTO dto) {
        Mandado mandado = mandadoMapper.toEntity(dto);

        if (dto.getArquivo() != null && !dto.getArquivo().isEmpty()) {
            String fileName = storageFileService.saveToFolder(dto.getArquivo(), "Mandados")
                    .getFileName();
            mandado.setArquivo(fileName);
        }

        // Set the user for the mandado
        User currentUser = userService.currentUser();
        if (currentUser == null) {
            throw new NotFoundException("Usuário não autenticado");
        }
        mandado.setUser(currentUser);

        mandado = mandadoRepository.save(mandado);
        
        return Response.<MandadoDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Mandado criado com sucesso")
                .data(mandadoMapper.toDTO(mandado))
                .build();
    }

    @Override
    @Transactional
    public Response<MandadoDTO> update(Long id, MandadoDTO dto) {
        Mandado mandado = mandadoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Mandado não encontrado"));

        mandado.setTipo(dto.getTipo());
        mandado.setNumeroProcesso(dto.getNumeroProcesso());
        mandado.setNomeRequerente(dto.getNomeRequerente());
        mandado.setNomeExecutado(dto.getNomeExecutado());
        mandado.setDataEmitido(dto.getDataEmitido());
        mandado.setDataValidade(dto.getDataValidade());
        mandado.setEmitidoPor(dto.getEmitidoPor());
        mandado.setEstado(dto.getEstado());
        mandado.setObservaciones(dto.getObservaciones());

        if (dto.getArquivo() != null && !dto.getArquivo().isEmpty()) {
            String fileName = storageFileService.saveToFolder(dto.getArquivo(), "Mandados")
                    .getFileName();
            mandado.setArquivo(fileName);
        } else if (dto.getArquivoUrl() != null) {
            mandado.setArquivo(dto.getArquivoUrl());
        }

        // Update user if provided in DTO
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            mandado.setUser(user);
        }


        mandado = mandadoRepository.save(mandado);
        
        return Response.<MandadoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Mandado actualizado com sucesso")
                .data(mandadoMapper.toDTO(mandado))
                .build();
    }

    @Override
    public Response<MandadoDTO> findById(Long id) {
        MandadoDTO dto = mandadoRepository.findById(id)
                .map(mandadoMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Mandado não encontrado"));

        return Response.<MandadoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Mandado encontrado")
                .data(dto)
                .build();
    }

    @Override
    public Response<List<MandadoDTO>> findAll() {
        User user = userService.currentUser();

        if (user == null) {
            throw new NotFoundException("Usuário não autenticado");
        }

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        boolean isInstrutor = user.getRoles().stream().anyMatch(role -> role.getName().equals("INSTRUTOR"));

        List<MandadoDTO> dtos;

        if (isAdmin) {
            dtos = mandadoRepository.findAll().stream()
                    .map(mandadoMapper::toDTO)
                    .collect(Collectors.toList());
        } else if (isInstrutor) {
            dtos = mandadoRepository.findAll().stream()
                    .filter(mandado -> mandado.getUser() != null && mandado.getUser().getId().equals(user.getId()))
                    .map(mandadoMapper::toDTO)
                    .collect(Collectors.toList());
        } else {
            // For other roles, only show mandados created by the current user
            dtos = mandadoRepository.findAll().stream()
                    .filter(mandado -> mandado.getUser() != null && mandado.getUser().getId().equals(user.getId()))
                    .map(mandadoMapper::toDTO)
                    .collect(Collectors.toList());
        }

        return Response.<List<MandadoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(dtos.isEmpty() ? "Nenhum mandado encontrado" : "Mandados encontrados")
                .data(dtos)
                .build();
    }

    @Override
    @Transactional
    public Response<Void> delete(Long id) {
        if (!mandadoRepository.existsById(id)) {
            throw new NotFoundException("Mandado não encontrado");
        }
        mandadoRepository.deleteById(id);

        return Response.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Mandado eliminado com sucesso")
                .build();
    }
}
