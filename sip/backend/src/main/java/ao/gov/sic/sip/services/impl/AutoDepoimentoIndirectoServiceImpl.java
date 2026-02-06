package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoDepoimentoIndirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoDepoimentoIndirecto;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoDepoimentoIndirectoMapper;
import ao.gov.sic.sip.repositories.AutoDepoimentoIndirectoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoDepoimentoIndirectoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoDepoimentoIndirectoServiceImpl implements AutoDepoimentoIndirectoService {
    private final AutoDepoimentoIndirectoRepository autoDepoimentoIndirectoRepository;
    private final AutoDepoimentoIndirectoMapper autoDepoimentoIndirectoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoDepoimentoIndirectoDTO> getById(Long id) {
        AutoDepoimentoIndirecto autoDepoimentoIndirecto = autoDepoimentoIndirectoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de depoimento indirecto não encontrado"));

        AutoDepoimentoIndirectoDTO autoDepoimentoIndirectoDTO = autoDepoimentoIndirectoMapper.autoDepoimentoIndirectoToAutoDepoimentoIndirectoDTO(autoDepoimentoIndirecto);

        return Response.<AutoDepoimentoIndirectoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de depoimento indirecto encontrado com sucesso")
                .data(autoDepoimentoIndirectoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoDepoimentoIndirectoDTO dto) {
        AutoDepoimentoIndirecto autoDepoimentoIndirecto = autoDepoimentoIndirectoMapper.autoDepoimentoIndirectoDTOToAutoDepoimentoIndirecto(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoDepoimentoIndirecto.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoDepoimentoIndirecto.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoDepoimentoIndirecto.setUser(user);
        }

        autoDepoimentoIndirectoRepository.save(autoDepoimentoIndirecto);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de depoimento indirecto criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoDepoimentoIndirectoRepository.existsById(id)) {
            throw new NotFoundException("Auto de depoimento indirecto não encontrado");
        }
        autoDepoimentoIndirectoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de depoimento indirecto eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoDepoimentoIndirectoDTO dto, Long id) {
        autoDepoimentoIndirectoRepository.findById(id).ifPresentOrElse(autoDepoimentoIndirecto -> {
            if (dto.getNumeroFolha() != null) {
                autoDepoimentoIndirecto.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoDepoimentoIndirecto.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getAssunto())) {
                autoDepoimentoIndirecto.setAssunto(dto.getAssunto());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoDepoimentoIndirecto.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoDepoimentoIndirecto.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoDepoimentoIndirecto.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoDepoimentoIndirecto.setUser(user);
            }
            autoDepoimentoIndirectoRepository.save(autoDepoimentoIndirecto);
        }, () -> {
            throw new NotFoundException("Auto de depoimento indirecto não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de depoimento indirecto actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoDepoimentoIndirectoDTO>> getAll() {
        List<AutoDepoimentoIndirectoDTO> autosDepoimentosIndirectos = autoDepoimentoIndirectoRepository.findAll()
                .stream().map(autoDepoimentoIndirectoMapper::autoDepoimentoIndirectoToAutoDepoimentoIndirectoDTO)
                .toList();

        return Response.<List<AutoDepoimentoIndirectoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosDepoimentosIndirectos.isEmpty() ? "Nenhum auto de depoimento indirecto encontrado" : "Autos de depoimentos indirectos encontrados com sucesso")
                .data(autosDepoimentosIndirectos)
                .build();
    }
}
