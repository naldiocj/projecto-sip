package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ProcessoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ProcessoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.ProcessoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessoServiceImpl implements ProcessoService {
    private final ProcessoRepository processoRepository;
    private final ProcessoMapper processoMapper;
    private final QueixosoRepository queixosoRepository;
    private final MagistradoRepository magistradoRepository;
    private final InstrutorRepository instrutorRepository;
    private final TipoCrimeRepository tipoCrimeRepository;
    private final ArguidoRepository arguidoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<ProcessoDTO> getById(Long id) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));

        ProcessoDTO processoDTO = processoMapper.processoToProcessoDTO(processo);

        return Response.<ProcessoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Processo encontrado com sucesso")
                .data(processoDTO)
                .build();
    }

    @Override
    public Response<?> create(ProcessoDTO dto) {
        Processo founded = processoRepository.findByNumero(dto.getNumero());
        if (founded != null) {
            throw new RuntimeException("Processo já existe");
        }

        Processo processo = processoMapper.processoDTOToProcesso(dto);

        if (dto.getQueixosoId() != null) {
            Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                    .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
            processo.setQueixoso(queixoso);
        }

        if (dto.getMagistradoId() != null) {
            Magistrado magistrado = magistradoRepository.findById(dto.getMagistradoId())
                    .orElseThrow(() -> new NotFoundException("Magistrado não encontrado"));
            processo.setMagistrado(magistrado);
        }

        if (dto.getInstrutorId() != null) {
            Instrutor instrutor = instrutorRepository.findById(dto.getInstrutorId())
                    .orElseThrow(() -> new NotFoundException("Instrutor não encontrado"));
            processo.setInstrutor(instrutor);
        }

        if (dto.getCrimesIds() != null && !dto.getCrimesIds().isEmpty()) {
            Set<TipoCrime> crimes = new HashSet<>(tipoCrimeRepository.findAllById(dto.getCrimesIds()));
            processo.setCrimes(crimes);
        }

        if (dto.getArguidosIds() != null && !dto.getArguidosIds().isEmpty()) {
            Set<Arguido> arguidos = new HashSet<>(arguidoRepository.findAllById(dto.getArguidosIds()));
            processo.setArguidos(arguidos);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            processo.setUser(user);
        }

        processoRepository.save(processo);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Processo criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!processoRepository.existsById(id)) {
            throw new NotFoundException("Processo não encontrado");
        }
        processoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Processo eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ProcessoDTO dto, Long id) {
        processoRepository.findById(id).ifPresentOrElse(processo -> {
            if (StringUtils.hasText(dto.getNome())) {
                processo.setNome(dto.getNome());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                processo.setDescricao(dto.getDescricao());
            }
            if (StringUtils.hasText(dto.getNumero())) {
                processo.setNumero(dto.getNumero());
            }
            if (dto.getTipoProcesso() != null) {
                processo.setTipoProcesso(dto.getTipoProcesso());
            }
            if (dto.getAno() != null) {
                processo.setAno(dto.getAno());
            }
            if (dto.getQueixosoId() != null) {
                Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                        .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
                processo.setQueixoso(queixoso);
            }
            if (dto.getMagistradoId() != null) {
                Magistrado magistrado = magistradoRepository.findById(dto.getMagistradoId())
                        .orElseThrow(() -> new NotFoundException("Magistrado não encontrado"));
                processo.setMagistrado(magistrado);
            }
            if (dto.getInstrutorId() != null) {
                Instrutor instrutor = instrutorRepository.findById(dto.getInstrutorId())
                        .orElseThrow(() -> new NotFoundException("Instrutor não encontrado"));
                processo.setInstrutor(instrutor);
            }
            if (dto.getCrimesIds() != null) {
                Set<TipoCrime> crimes = new HashSet<>(tipoCrimeRepository.findAllById(dto.getCrimesIds()));
                processo.setCrimes(crimes);
            }
            if (dto.getArguidosIds() != null) {
                Set<Arguido> arguidos = new HashSet<>(arguidoRepository.findAllById(dto.getArguidosIds()));
                processo.setArguidos(arguidos);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                processo.setUser(user);
            }
            processoRepository.save(processo);
        }, () -> {
            throw new NotFoundException("Processo não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Processo actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<ProcessoDTO>> getAll() {
        List<ProcessoDTO> processos = processoRepository.findAll()
                .stream().map(processoMapper::processoToProcessoDTO)
                .toList();

        return Response.<List<ProcessoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(processos.isEmpty() ? "Nenhum processo encontrado" : "Processos encontrados com sucesso")
                .data(processos)
                .build();
    }
}
