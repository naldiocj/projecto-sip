package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoPessoaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoReconhecimentoFisicoDirectoPessoa;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoReconhecimentoFisicoDirectoPessoaMapper;
import ao.gov.sic.sip.repositories.AutoReconhecimentoFisicoDirectoPessoaRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoReconhecimentoFisicoDirectoPessoaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoReconhecimentoFisicoDirectoPessoaServiceImpl implements AutoReconhecimentoFisicoDirectoPessoaService {
    private final AutoReconhecimentoFisicoDirectoPessoaRepository autoReconhecimentoFisicoDirectoPessoaRepository;
    private final AutoReconhecimentoFisicoDirectoPessoaMapper autoReconhecimentoFisicoDirectoPessoaMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoReconhecimentoFisicoDirectoPessoaDTO> getById(Long id) {
        AutoReconhecimentoFisicoDirectoPessoa autoReconhecimentoFisicoDirectoPessoa = autoReconhecimentoFisicoDirectoPessoaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de reconhecimento físico directo de pessoa não encontrado"));

        AutoReconhecimentoFisicoDirectoPessoaDTO autoReconhecimentoFisicoDirectoPessoaDTO = autoReconhecimentoFisicoDirectoPessoaMapper.autoReconhecimentoFisicoDirectoPessoaToAutoReconhecimentoFisicoDirectoPessoaDTO(autoReconhecimentoFisicoDirectoPessoa);

        return Response.<AutoReconhecimentoFisicoDirectoPessoaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento físico directo de pessoa encontrado com sucesso")
                .data(autoReconhecimentoFisicoDirectoPessoaDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoReconhecimentoFisicoDirectoPessoaDTO dto) {
        AutoReconhecimentoFisicoDirectoPessoa autoReconhecimentoFisicoDirectoPessoa = autoReconhecimentoFisicoDirectoPessoaMapper.autoReconhecimentoFisicoDirectoPessoaDTOToAutoReconhecimentoFisicoDirectoPessoa(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoReconhecimentoFisicoDirectoPessoa.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoReconhecimentoFisicoDirectoPessoa.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoReconhecimentoFisicoDirectoPessoa.setUser(user);
        }

        autoReconhecimentoFisicoDirectoPessoaRepository.save(autoReconhecimentoFisicoDirectoPessoa);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de reconhecimento físico directo de pessoa criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoReconhecimentoFisicoDirectoPessoaRepository.existsById(id)) {
            throw new NotFoundException("Auto de reconhecimento físico directo de pessoa não encontrado");
        }
        autoReconhecimentoFisicoDirectoPessoaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento físico directo de pessoa eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoReconhecimentoFisicoDirectoPessoaDTO dto, Long id) {
        autoReconhecimentoFisicoDirectoPessoaRepository.findById(id).ifPresentOrElse(autoReconhecimentoFisicoDirectoPessoa -> {
            if (dto.getNumeroFolha() != null) {
                autoReconhecimentoFisicoDirectoPessoa.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoReconhecimentoFisicoDirectoPessoa.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoReconhecimentoFisicoDirectoPessoa.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoReconhecimentoFisicoDirectoPessoa.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoReconhecimentoFisicoDirectoPessoa.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoReconhecimentoFisicoDirectoPessoa.setUser(user);
            }
            autoReconhecimentoFisicoDirectoPessoaRepository.save(autoReconhecimentoFisicoDirectoPessoa);
        }, () -> {
            throw new NotFoundException("Auto de reconhecimento físico directo de pessoa não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento físico directo de pessoa actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoReconhecimentoFisicoDirectoPessoaDTO>> getAll() {
        List<AutoReconhecimentoFisicoDirectoPessoaDTO> autosReconhecimentoFisicoDirectoPessoas = autoReconhecimentoFisicoDirectoPessoaRepository.findAll()
                .stream().map(autoReconhecimentoFisicoDirectoPessoaMapper::autoReconhecimentoFisicoDirectoPessoaToAutoReconhecimentoFisicoDirectoPessoaDTO)
                .toList();

        return Response.<List<AutoReconhecimentoFisicoDirectoPessoaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosReconhecimentoFisicoDirectoPessoas.isEmpty() ? "Nenhum auto de reconhecimento físico directo de pessoa encontrado" : "Autos de reconhecimento físico directo de pessoas encontrados com sucesso")
                .data(autosReconhecimentoFisicoDirectoPessoas)
                .build();
    }
}
