package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoApreensaoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.AutoApreensaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoApreensaoServiceImpl implements AutoApreensaoService {
    private final AutoApreensaoRepository autoApreensaoRepository;
    private final AutoApreensaoMapper autoApreensaoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final ParticipanteAutoApreensaoRepository participanteAutoApreensaoRepository;
    private final ObjectoAutoApreensaoRepository objectoAutoApreensaoRepository;

    @Override
    public Response<AutoApreensaoDTO> getById(Long id) {
        AutoApreensao autoApreensao = autoApreensaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de apreensão não encontrado"));

        AutoApreensaoDTO autoApreensaoDTO = autoApreensaoMapper.autoApreensaoToAutoApreensaoDTO(autoApreensao);

        return Response.<AutoApreensaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de apreensão encontrado com sucesso")
                .data(autoApreensaoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoApreensaoDTO dto) {
        AutoApreensao autoApreensao = autoApreensaoMapper.autoApreensaoDTOToAutoApreensao(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoApreensao.setEndereco(endereco);
        } else {
            autoApreensao.setEndereco(null);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoApreensao.setProcesso(processo);
        } else {
            throw new NotFoundException("Processo não encontrado");
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoApreensao.setUser(user);
        }

        if (dto.getPeritosApreensoesIds() != null && !dto.getPeritosApreensoesIds().isEmpty()) {
            List<ParticipanteAutoApreensao> participantes = participanteAutoApreensaoRepository.findAllById(dto.getPeritosApreensoesIds());
            autoApreensao.setPeritosApreensoes(participantes);
        }

        if (dto.getObjectoApreensoesIds() != null && !dto.getObjectoApreensoesIds().isEmpty()) {
            List<ObjectoAutoApreensao> objectos = objectoAutoApreensaoRepository.findAllById(dto.getObjectoApreensoesIds());
            autoApreensao.setObjectoApreensoes(objectos);
        }

        autoApreensaoRepository.save(autoApreensao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de apreensão criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoApreensaoRepository.existsById(id)) {
            throw new NotFoundException("Auto de apreensão não encontrado");
        }
        autoApreensaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de apreensão eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoApreensaoDTO dto, Long id) {
        autoApreensaoRepository.findById(id).ifPresentOrElse(autoApreensao -> {
            if (dto.getNumeroFolha() != null) {
                autoApreensao.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoApreensao.setDataEmissao(dto.getDataEmissao());
            }
            if (dto.getNaQualidadeDe() != null) {
                autoApreensao.setNaQualidadeDe(dto.getNaQualidadeDe());
            }
            if (StringUtils.hasText(dto.getNaturezaCaracteristicas())) {
                autoApreensao.setNaturezaCaracteristicas(dto.getNaturezaCaracteristicas());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoApreensao.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoApreensao.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoApreensao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoApreensao.setUser(user);
            }
            if (dto.getPeritosApreensoesIds() != null) {
                List<ParticipanteAutoApreensao> participantes = participanteAutoApreensaoRepository.findAllById(dto.getPeritosApreensoesIds());
                autoApreensao.setPeritosApreensoes(participantes);
            }
            if (dto.getObjectoApreensoesIds() != null) {
                List<ObjectoAutoApreensao> objectos = objectoAutoApreensaoRepository.findAllById(dto.getObjectoApreensoesIds());
                autoApreensao.setObjectoApreensoes(objectos);
            }
            autoApreensaoRepository.save(autoApreensao);
        }, () -> {
            throw new NotFoundException("Auto de apreensão não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de apreensão actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoApreensaoDTO>> getAll() {
        List<AutoApreensaoDTO> autosApreensoes = autoApreensaoRepository.findAll()
                .stream().map(autoApreensaoMapper::autoApreensaoToAutoApreensaoDTO)
                .toList();

        return Response.<List<AutoApreensaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosApreensoes.isEmpty() ? "Nenhum auto de apreensão encontrado" : "Autos de apreensões encontrados com sucesso")
                .data(autosApreensoes)
                .build();
    }
}
