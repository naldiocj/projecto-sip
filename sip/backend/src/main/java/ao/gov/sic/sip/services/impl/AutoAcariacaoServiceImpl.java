package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoAcariacaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoAcariacao;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoAcariacaoMapper;
import ao.gov.sic.sip.repositories.AutoAcariacaoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoAcariacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoAcariacaoServiceImpl implements AutoAcariacaoService {
    private final AutoAcariacaoRepository autoAcariacaoRepository;
    private final AutoAcariacaoMapper autoAcariacaoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoAcariacaoDTO> getById(Long id) {
        AutoAcariacao autoAcariacao = autoAcariacaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de acariação não encontrado"));

        AutoAcariacaoDTO autoAcariacaoDTO = autoAcariacaoMapper.autoAcariacaoToAutoAcariacaoDTO(autoAcariacao);

        return Response.<AutoAcariacaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de acariação encontrado com sucesso")
                .data(autoAcariacaoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoAcariacaoDTO dto) {
        AutoAcariacao autoAcariacao = autoAcariacaoMapper.autoAcariacaoDTOToAutoAcariacao(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoAcariacao.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoAcariacao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoAcariacao.setUser(user);
        }

        autoAcariacaoRepository.save(autoAcariacao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de acariação criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoAcariacaoRepository.existsById(id)) {
            throw new NotFoundException("Auto de acariação não encontrado");
        }
        autoAcariacaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de acariação eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoAcariacaoDTO dto, Long id) {
        autoAcariacaoRepository.findById(id).ifPresentOrElse(autoAcariacao -> {
            if (dto.getNumeroFolha() != null) {
                autoAcariacao.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoAcariacao.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoAcariacao.setDescricao(dto.getDescricao());
            }
            if (dto.getTipoDeclaracao() != null) {
                autoAcariacao.setTipoDeclaracao(dto.getTipoDeclaracao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoAcariacao.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoAcariacao.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoAcariacao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoAcariacao.setUser(user);
            }
            autoAcariacaoRepository.save(autoAcariacao);
        }, () -> {
            throw new NotFoundException("Auto de acariação não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de acariação actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoAcariacaoDTO>> getAll() {
        List<AutoAcariacaoDTO> autosAcariacoes = autoAcariacaoRepository.findAll()
                .stream().map(autoAcariacaoMapper::autoAcariacaoToAutoAcariacaoDTO)
                .toList();

        return Response.<List<AutoAcariacaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosAcariacoes.isEmpty() ? "Nenhum auto de acariação encontrado" : "Autos de acariações encontrados com sucesso")
                .data(autosAcariacoes)
                .build();
    }
}
