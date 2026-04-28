package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.*;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.EstadoProcesso;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ProcessoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.DirecaoService;
import ao.gov.sic.sip.services.ProcessoService;
import ao.gov.sic.sip.services.UserService;
import ao.gov.sic.sip.utils.ProcessoSpecifications;
import ao.gov.sic.sip.utils.ValidarDocumento;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import java.util.*;

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
    private final UserService userService;
    private final SecretariaRepository secretariaRepository;

    @Qualifier("objectMapper")
    private final ObjectMapper mapper;
    private final DirecaoService direcaoService;
    private final DirecaoRepository direcaoRepository;

    @Override
    public Response<ProcessoDetailDTO> getById(Long id) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado"));

        ProcessoDetailDTO processoDetailDTO = processoMapper.processoToProcessoDetailDTO(processo);

        return Response.<ProcessoDetailDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Processo encontrado com sucesso")
                .data(processoDetailDTO)
                .build();
    }

    @Override
    public Response<ProcessoDetailDTO> getByNumero(String numero) {

        if (!StringUtils.hasText(numero)) {
            throw new RuntimeException("O número do processo é obrigatório");
        }

        String numeroConvertido = numero.replaceFirst("-", "/");

        Processo processo = processoRepository.findFirstByNumero(numeroConvertido);

        if (processo == null) {
            throw new NotFoundException("Processo não encontrado");
        }

        ProcessoDetailDTO processoDetailDTO = processoMapper.processoToProcessoDetailDTO(processo);
        processoDetailDTO.setCreatedAt(processo.getCreatedAt());

        return Response.<ProcessoDetailDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Processo encontrado com sucesso")
                .data(processoDetailDTO)
                .build();
    }

    @Transactional
    @Override
    public Response<?> create(ProcessoDTO dto) {
        boolean isValid = ValidarDocumento.isValidNumeroProcesso(dto.getNumero());

        if (!isValid) {
            throw new RuntimeException("O número do processo é inválido");
        }

        Processo founded = processoRepository.findFirstByNumero(dto.getNumero());
        if (founded != null) {
            throw new RuntimeException("Este processo já foi registado");
        }

        Processo processo = processoMapper.processoDTOToProcesso(dto);

        if (dto.getQueixosoId() != null) {
            Queixoso queixoso = queixosoRepository.findById(dto.getQueixosoId())
                    .orElseThrow(() -> new NotFoundException("Queixoso não encontrado"));
            processo.setQueixoso(queixoso);
        } else {
            processo.setQueixoso(null);
        }

        if (dto.getMagistradoId() != null) {
            Magistrado magistrado = magistradoRepository.findById(dto.getMagistradoId())
                    .orElseThrow(() -> new NotFoundException("Magistrado não encontrado"));
            processo.setMagistrado(magistrado);
        } else {
            processo.setMagistrado(null);
        }

        if (dto.getInstrutorId() != null) {
            Instrutor instrutor = instrutorRepository.findById(dto.getInstrutorId())
                    .orElseThrow(() -> new NotFoundException("Instrutor não encontrado"));
            processo.setInstrutor(instrutor);
        } else {
            processo.setInstrutor(null);
        }

        if (dto.getDirecaoId() != null) {
            Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                    .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
            processo.setDirecao(direcao);
        } else {
            processo.setDirecao(null);
        }

        if (dto.getSecretariaId() != null) {
            Secretaria secretaria = secretariaRepository.findById(dto.getSecretariaId())
                    .orElseThrow(() -> new NotFoundException("Secretaria não encontrada"));
            processo.setSecretaria(secretaria);
        } else {
            processo.setSecretaria(null);
        }

        if (dto.getCrimesIds() != null && !dto.getCrimesIds().isEmpty()) {
            Set<TipoCrime> crimes = new HashSet<>(tipoCrimeRepository.findAllById(dto.getCrimesIds()));

            for (TipoCrime crime : crimes) {
                processo.getCrimes().add(crime);
            }
        }

        if (dto.getArguidosIds() != null && !dto.getArguidosIds().isEmpty()) {
            Set<Arguido> arguidos = new HashSet<>(arguidoRepository.findAllById(dto.getArguidosIds()));
            processo.setArguidos(arguidos);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            processo.setUser(user);
        } else {
            User user = userService.currentUser();
            processo.setUser(user);
        }

        if (dto.getEstadoProcesso() == null) {
            processo.setEstadoProcesso(EstadoProcesso.EM_INSTRUCAO);
        } else {
            processo.setEstadoProcesso(dto.getEstadoProcesso());
        }

        processoRepository.saveAndFlush(processo);

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
            if (dto.getDirecaoId() != null) {
                Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                        .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
                processo.setDirecao(direcao);
            }
            if (dto.getSecretariaId() != null) {
                Secretaria secretaria = secretariaRepository.findById(dto.getSecretariaId())
                        .orElseThrow(() -> new NotFoundException("Secretaria não encontrada"));
                processo.setSecretaria(secretaria);
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

            if (dto.getEstadoProcesso() != null) {
                processo.setEstadoProcesso(dto.getEstadoProcesso());
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
    public Response<List<ProcessoResDTO>> getAll(String term) {

        User user = userService.currentUser();

        // 1. Build the specification based on the term
        Specification<Processo> spec = ProcessoSpecifications.hasTerm(term);

        // 2. Fetch filtered results directly from DB
        List<ProcessoResDTO> processos = new ArrayList<>();

        boolean isAdmin = user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"));
        boolean isInstrutor = user.getRoles().stream().anyMatch(role -> role.getName().equals("INSTRUTOR"));
        boolean isSecretaria = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA"));
        boolean isSecretariaGeral = user.getRoles().stream().anyMatch(role -> role.getName().equals("SECRETARIA_GERAL"));



        if (isAdmin) {
            processos = processoRepository.findAll(spec)
                    .stream()
                    .map(processoMapper::processoToProcessoResDTO)
                    .toList();
        } else if (isInstrutor) {
            Instrutor instrutor = instrutorRepository.findAll().stream()
                    .filter(i -> i.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(()-> new NotFoundException("Instrutor não encontrado"));

            processos = processoRepository.findAll(spec)
                    .stream()
                    .filter(processo -> processo.getInstrutor() != null && processo.getInstrutor().getId().equals(instrutor.getId()))
                    .map(processoMapper::processoToProcessoResDTO)
                    .peek(p -> {
                        if (p.getDirecao() == null) {
                            p.setDirecao(null);
                        }
                    })
                    .toList();
        } else if (isSecretaria) {
            Secretaria secretaria = secretariaRepository.findAll()
                    .stream()
                    .filter(s -> s.getUser().getId().equals(user.getId())).findFirst()
                    .orElseThrow(() -> new NotFoundException("Secretaria não encontrada"));

            processos = processoRepository.findAll(spec)
                    .stream()
                    .filter(processo -> processo.getDirecao() != null && processo.getDirecao().getId().equals(secretaria.getDirecao().getId()))
                    .map(processoMapper::processoToProcessoResDTO)
                    .toList();
        } else if (isSecretariaGeral) {
            processos = processoRepository.findAll(spec)
                    .stream()
                    .map(processoMapper::processoToProcessoResDTO)
                    .toList();
        }
        else {
            processos = processoRepository.findAll(spec)
                    .stream()
                    .filter(processo -> processo.getUser().getId().equals(user.getId()))
                    .map(processoMapper::processoToProcessoResDTO)
                    .toList();
        }

        return Response.<List<ProcessoResDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(processos.isEmpty() ? "Nenhum processo encontrado" : "Sucesso")
                .data(processos)
                .build();
    }

    @Override
    @Transactional
    public void patchProcessoById(Long id, UpdateProcessoDTO dto) {
        Processo processo = processoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Processo não encontrado com id: " + id));

        if (dto.getDirecaoId() != null) {
            Optional<Direcao> direcao = direcaoRepository.findById(dto.getDirecaoId());
            direcao.ifPresent(processo::setDirecao);
        }

        if (dto.getInstrutorId() != null) {
            Optional<Instrutor> instrutor = instrutorRepository.findById(dto.getInstrutorId());
            instrutor.ifPresent(processo::setInstrutor);
        }

        processoRepository.save(processo);
    }
}
