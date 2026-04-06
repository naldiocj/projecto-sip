package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.EditalDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Edital;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.EditalMapper;
import ao.gov.sic.sip.repositories.EditalRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.EditalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EditalServiceImpl implements EditalService {
    private final EditalRepository editalRepository;
    private final EditalMapper editalMapper;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<EditalDTO> getById(Long id) {
        Edital edital = editalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Edital não encontrado"));

        EditalDTO editalDTO = editalMapper.editalToEditalDTO(edital);

        return Response.<EditalDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Edital encontrado com sucesso")
                .data(editalDTO)
                .build();
    }

    @Override
    public Response<?> create(EditalDTO dto) {
        Edital edital = editalMapper.editalDTOToEdital(dto);

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            edital.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            edital.setUser(user);
        }

        editalRepository.save(edital);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Edital criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!editalRepository.existsById(id)) {
            throw new NotFoundException("Edital não encontrado");
        }
        editalRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Edital eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(EditalDTO dto, Long id) {
        editalRepository.findById(id).ifPresentOrElse(edital -> {
            if (dto.getDataEmissao() != null) {
                edital.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                edital.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                edital.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                edital.setUser(user);
            }
            editalRepository.save(edital);
        }, () -> {
            throw new NotFoundException("Edital não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Edital actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<EditalDTO>> getAll() {
        List<EditalDTO> editais = editalRepository.findAll()
                .stream().map(editalMapper::editalToEditalDTO)
                .toList();

        return Response.<List<EditalDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(editais.isEmpty() ? "Nenhum edital encontrado" : "Editais encontrados com sucesso")
                .data(editais)
                .build();
    }
}
