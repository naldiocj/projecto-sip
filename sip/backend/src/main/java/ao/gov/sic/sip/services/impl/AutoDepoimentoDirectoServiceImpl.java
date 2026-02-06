package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoDepoimentoDirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoDepoimentoDirecto;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoDepoimentoDirectoMapper;
import ao.gov.sic.sip.repositories.AutoDepoimentoDirectoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoDepoimentoDirectoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDepoimentoDirectoServiceImpl implements AutoDepoimentoDirectoService {
    private final AutoDepoimentoDirectoRepository autoDepoimentoDirectoRepository;
    private final AutoDepoimentoDirectoMapper autoDepoimentoDirectoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoDepoimentoDirectoDTO> getById(Long id) {
        AutoDepoimentoDirecto autoDepoimentoDirecto = autoDepoimentoDirectoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de depoimento directo não encontrado"));

        AutoDepoimentoDirectoDTO autoDepoimentoDirectoDTO = autoDepoimentoDirectoMapper.autoDepoimentoDirectoToAutoDepoimentoDirectoDTO(autoDepoimentoDirecto);

        return Response.<AutoDepoimentoDirectoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de depoimento directo encontrado com sucesso")
                .data(autoDepoimentoDirectoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoDepoimentoDirectoDTO dto) {
        AutoDepoimentoDirecto autoDepoimentoDirecto = autoDepoimentoDirectoMapper.autoDepoimentoDirectoDTOToAutoDepoimentoDirecto(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoDepoimentoDirecto.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoDepoimentoDirecto.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoDepoimentoDirecto.setUser(user);
        }

        autoDepoimentoDirectoRepository.save(autoDepoimentoDirecto);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de depoimento directo criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoDepoimentoDirectoRepository.existsById(id)) {
            throw new NotFoundException("Auto de depoimento directo não encontrado");
        }
        autoDepoimentoDirectoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de depoimento directo eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoDepoimentoDirectoDTO dto, Long id) {
        autoDepoimentoDirectoRepository.findById(id).ifPresentOrElse(autoDepoimentoDirecto -> {
            if (dto.getNumeroFolha() != null) {
                autoDepoimentoDirecto.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoDepoimentoDirecto.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getAssunto())) {
                autoDepoimentoDirecto.setAssunto(dto.getAssunto());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoDepoimentoDirecto.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoDepoimentoDirecto.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoDepoimentoDirecto.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoDepoimentoDirecto.setUser(user);
            }
            autoDepoimentoDirectoRepository.save(autoDepoimentoDirecto);
        }, () -> {
            throw new NotFoundException("Auto de depoimento directo não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de depoimento directo actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoDepoimentoDirectoDTO>> getAll() {
        List<AutoDepoimentoDirectoDTO> autosDepoimentosDirectos = autoDepoimentoDirectoRepository.findAll()
                .stream().map(autoDepoimentoDirectoMapper::autoDepoimentoDirectoToAutoDepoimentoDirectoDTO)
                .toList();

        return Response.<List<AutoDepoimentoDirectoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosDepoimentosDirectos.isEmpty() ? "Nenhum auto de depoimento directo encontrado" : "Autos de depoimentos directos encontrados com sucesso")
                .data(autosDepoimentosDirectos)
                .build();
    }
}
