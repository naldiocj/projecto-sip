package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoReconstituicaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoReconstituicao;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoReconstituicaoMapper;
import ao.gov.sic.sip.repositories.AutoReconstituicaoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoReconstituicaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoReconstituicaoServiceImpl implements AutoReconstituicaoService {
    private final AutoReconstituicaoRepository autoReconstituicaoRepository;
    private final AutoReconstituicaoMapper autoReconstituicaoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoReconstituicaoDTO> getById(Long id) {
        AutoReconstituicao autoReconstituicao = autoReconstituicaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de reconstituição não encontrado"));

        AutoReconstituicaoDTO autoReconstituicaoDTO = autoReconstituicaoMapper.autoReconstituicaoToAutoReconstituicaoDTO(autoReconstituicao);

        return Response.<AutoReconstituicaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconstituição encontrado com sucesso")
                .data(autoReconstituicaoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoReconstituicaoDTO dto) {
        AutoReconstituicao autoReconstituicao = autoReconstituicaoMapper.autoReconstituicaoDTOToAutoReconstituicao(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoReconstituicao.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoReconstituicao.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoReconstituicao.setUser(user);
        }

        autoReconstituicaoRepository.save(autoReconstituicao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de reconstituição criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoReconstituicaoRepository.existsById(id)) {
            throw new NotFoundException("Auto de reconstituição não encontrado");
        }
        autoReconstituicaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconstituição eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoReconstituicaoDTO dto, Long id) {
        autoReconstituicaoRepository.findById(id).ifPresentOrElse(autoReconstituicao -> {
            if (dto.getNumeroFolha() != null) {
                autoReconstituicao.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoReconstituicao.setDataEmissao(dto.getDataEmissao());
            }
            if (dto.getDataInicio() != null) {
                autoReconstituicao.setDataInicio(dto.getDataInicio());
            }
            if (dto.getDataFim() != null) {
                autoReconstituicao.setDataFim(dto.getDataFim());
            }
            if (StringUtils.hasText(dto.getMeiosUtilizados())) {
                autoReconstituicao.setMeiosUtilizados(dto.getMeiosUtilizados());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                autoReconstituicao.setDescricao(dto.getDescricao());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoReconstituicao.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoReconstituicao.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoReconstituicao.setUser(user);
            }
            autoReconstituicaoRepository.save(autoReconstituicao);
        }, () -> {
            throw new NotFoundException("Auto de reconstituição não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconstituição actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoReconstituicaoDTO>> getAll() {
        List<AutoReconstituicaoDTO> autosReconstituicoes = autoReconstituicaoRepository.findAll()
                .stream().map(autoReconstituicaoMapper::autoReconstituicaoToAutoReconstituicaoDTO)
                .toList();

        return Response.<List<AutoReconstituicaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosReconstituicoes.isEmpty() ? "Nenhum auto de reconstituição encontrado" : "Autos de reconstituições encontrados com sucesso")
                .data(autosReconstituicoes)
                .build();
    }
}
