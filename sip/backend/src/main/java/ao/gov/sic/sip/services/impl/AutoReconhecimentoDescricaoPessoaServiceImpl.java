package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoReconhecimentoDescricaoPessoaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoReconhecimentoDescricaoPessoa;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoReconhecimentoDescricaoPessoaMapper;
import ao.gov.sic.sip.repositories.AutoReconhecimentoDescricaoPessoaRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoReconhecimentoDescricaoPessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoReconhecimentoDescricaoPessoaServiceImpl implements AutoReconhecimentoDescricaoPessoaService {
    private final AutoReconhecimentoDescricaoPessoaRepository autoReconhecimentoDescricaoPessoaRepository;
    private final AutoReconhecimentoDescricaoPessoaMapper autoReconhecimentoDescricaoPessoaMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoReconhecimentoDescricaoPessoaDTO> getById(Long id) {
        AutoReconhecimentoDescricaoPessoa autoReconhecimentoDescricaoPessoa = autoReconhecimentoDescricaoPessoaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de reconhecimento por descrição de pessoa não encontrado"));

        AutoReconhecimentoDescricaoPessoaDTO autoReconhecimentoDescricaoPessoaDTO = autoReconhecimentoDescricaoPessoaMapper.autoReconhecimentoDescricaoPessoaToAutoReconhecimentoDescricaoPessoaDTO(autoReconhecimentoDescricaoPessoa);

        return Response.<AutoReconhecimentoDescricaoPessoaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento por descrição de pessoa encontrado com sucesso")
                .data(autoReconhecimentoDescricaoPessoaDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoReconhecimentoDescricaoPessoaDTO dto) {
        AutoReconhecimentoDescricaoPessoa autoReconhecimentoDescricaoPessoa = autoReconhecimentoDescricaoPessoaMapper.autoReconhecimentoDescricaoPessoaDTOToAutoReconhecimentoDescricaoPessoa(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoReconhecimentoDescricaoPessoa.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoReconhecimentoDescricaoPessoa.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoReconhecimentoDescricaoPessoa.setUser(user);
        }

        autoReconhecimentoDescricaoPessoaRepository.save(autoReconhecimentoDescricaoPessoa);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de reconhecimento por descrição de pessoa criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoReconhecimentoDescricaoPessoaRepository.existsById(id)) {
            throw new NotFoundException("Auto de reconhecimento por descrição de pessoa não encontrado");
        }
        autoReconhecimentoDescricaoPessoaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento por descrição de pessoa eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoReconhecimentoDescricaoPessoaDTO dto, Long id) {
        autoReconhecimentoDescricaoPessoaRepository.findById(id).ifPresentOrElse(autoReconhecimentoDescricaoPessoa -> {
            if (dto.getNumeroFolha() != null) {
                autoReconhecimentoDescricaoPessoa.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoReconhecimentoDescricaoPessoa.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoReconhecimentoDescricaoPessoa.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoReconhecimentoDescricaoPessoa.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoReconhecimentoDescricaoPessoa.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoReconhecimentoDescricaoPessoa.setUser(user);
            }
            autoReconhecimentoDescricaoPessoaRepository.save(autoReconhecimentoDescricaoPessoa);
        }, () -> {
            throw new NotFoundException("Auto de reconhecimento por descrição de pessoa não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento por descrição de pessoa actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoReconhecimentoDescricaoPessoaDTO>> getAll() {
        List<AutoReconhecimentoDescricaoPessoaDTO> autosReconhecimentoDescricaoPessoas = autoReconhecimentoDescricaoPessoaRepository.findAll()
                .stream().map(autoReconhecimentoDescricaoPessoaMapper::autoReconhecimentoDescricaoPessoaToAutoReconhecimentoDescricaoPessoaDTO)
                .toList();

        return Response.<List<AutoReconhecimentoDescricaoPessoaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosReconhecimentoDescricaoPessoas.isEmpty() ? "Nenhum auto de reconhecimento por descrição de pessoa encontrado" : "Autos de reconhecimento por descrição de pessoas encontrados com sucesso")
                .data(autosReconhecimentoDescricaoPessoas)
                .build();
    }
}
