package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoExameDirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoExameDirectoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.AutoExameDirectoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoExameDirectoServiceImpl implements AutoExameDirectoService {
    private final AutoExameDirectoRepository autoExameDirectoRepository;
    private final AutoExameDirectoMapper autoExameDirectoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final PeritoExameDirectoRepository peritoExameDirectoRepository;

    @Override
    public Response<AutoExameDirectoDTO> getById(Long id) {
        AutoExameDirecto autoExameDirecto = autoExameDirectoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de exame directo não encontrado"));

        AutoExameDirectoDTO autoExameDirectoDTO = autoExameDirectoMapper.autoExameDirectoToAutoExameDirectoDTO(autoExameDirecto);

        return Response.<AutoExameDirectoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de exame directo encontrado com sucesso")
                .data(autoExameDirectoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoExameDirectoDTO dto) {
        AutoExameDirecto autoExameDirecto = autoExameDirectoMapper.autoExameDirectoDTOToAutoExameDirecto(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoExameDirecto.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoExameDirecto.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoExameDirecto.setUser(user);
        }

        if (dto.getPeritoExameDirectosIds() != null && !dto.getPeritoExameDirectosIds().isEmpty()) {
            List<PeritoExameDirecto> peritos = peritoExameDirectoRepository.findAllById(dto.getPeritoExameDirectosIds());
            autoExameDirecto.setPeritoExameDirectos(peritos);
        }

        autoExameDirectoRepository.save(autoExameDirecto);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de exame directo criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoExameDirectoRepository.existsById(id)) {
            throw new NotFoundException("Auto de exame directo não encontrado");
        }
        autoExameDirectoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de exame directo eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoExameDirectoDTO dto, Long id) {
        autoExameDirectoRepository.findById(id).ifPresentOrElse(autoExameDirecto -> {
            if (dto.getNumeroFolha() != null) {
                autoExameDirecto.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoExameDirecto.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getArtigoExaminado())) {
                autoExameDirecto.setArtigoExaminado(dto.getArtigoExaminado());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoExameDirecto.setDescricao(dto.getDescricao());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoExameDirecto.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoExameDirecto.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoExameDirecto.setUser(user);
            }
            if (dto.getPeritoExameDirectosIds() != null) {
                List<PeritoExameDirecto> peritos = peritoExameDirectoRepository.findAllById(dto.getPeritoExameDirectosIds());
                autoExameDirecto.setPeritoExameDirectos(peritos);
            }
            autoExameDirectoRepository.save(autoExameDirecto);
        }, () -> {
            throw new NotFoundException("Auto de exame directo não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de exame directo actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoExameDirectoDTO>> getAll() {
        List<AutoExameDirectoDTO> autosExamesDirectos = autoExameDirectoRepository.findAll()
                .stream().map(autoExameDirectoMapper::autoExameDirectoToAutoExameDirectoDTO)
                .toList();

        return Response.<List<AutoExameDirectoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosExamesDirectos.isEmpty() ? "Nenhum auto de exame directo encontrado" : "Autos de exames directos encontrados com sucesso")
                .data(autosExamesDirectos)
                .build();
    }
}
