package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoReconhecimentoFisicoDirectoObjectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.AutoReconhecimentoFisicoDirectoObjecto;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoReconhecimentoFisicoDirectoObjectoMapper;
import ao.gov.sic.sip.repositories.AutoReconhecimentoFisicoDirectoObjectoRepository;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.AutoReconhecimentoFisicoDirectoObjectoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoReconhecimentoFisicoDirectoObjectoServiceImpl implements AutoReconhecimentoFisicoDirectoObjectoService {
    private final AutoReconhecimentoFisicoDirectoObjectoRepository autoReconhecimentoFisicoDirectoObjectoRepository;
    private final AutoReconhecimentoFisicoDirectoObjectoMapper autoReconhecimentoFisicoDirectoObjectoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<AutoReconhecimentoFisicoDirectoObjectoDTO> getById(Long id) {
        AutoReconhecimentoFisicoDirectoObjecto autoReconhecimentoFisicoDirectoObjecto = autoReconhecimentoFisicoDirectoObjectoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de reconhecimento físico directo de objecto não encontrado"));

        AutoReconhecimentoFisicoDirectoObjectoDTO autoReconhecimentoFisicoDirectoObjectoDTO = autoReconhecimentoFisicoDirectoObjectoMapper.autoReconhecimentoFisicoDirectoObjectoToAutoReconhecimentoFisicoDirectoObjectoDTO(autoReconhecimentoFisicoDirectoObjecto);

        return Response.<AutoReconhecimentoFisicoDirectoObjectoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento físico directo de objecto encontrado com sucesso")
                .data(autoReconhecimentoFisicoDirectoObjectoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoReconhecimentoFisicoDirectoObjectoDTO dto) {
        AutoReconhecimentoFisicoDirectoObjecto autoReconhecimentoFisicoDirectoObjecto = autoReconhecimentoFisicoDirectoObjectoMapper.autoReconhecimentoFisicoDirectoObjectoDTOToAutoReconhecimentoFisicoDirectoObjecto(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoReconhecimentoFisicoDirectoObjecto.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoReconhecimentoFisicoDirectoObjecto.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoReconhecimentoFisicoDirectoObjecto.setUser(user);
        }

        autoReconhecimentoFisicoDirectoObjectoRepository.save(autoReconhecimentoFisicoDirectoObjecto);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de reconhecimento físico directo de objecto criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoReconhecimentoFisicoDirectoObjectoRepository.existsById(id)) {
            throw new NotFoundException("Auto de reconhecimento físico directo de objecto não encontrado");
        }
        autoReconhecimentoFisicoDirectoObjectoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento físico directo de objecto eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoReconhecimentoFisicoDirectoObjectoDTO dto, Long id) {
        autoReconhecimentoFisicoDirectoObjectoRepository.findById(id).ifPresentOrElse(autoReconhecimentoFisicoDirectoObjecto -> {
            if (dto.getNumeroFolha() != null) {
                autoReconhecimentoFisicoDirectoObjecto.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoReconhecimentoFisicoDirectoObjecto.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoReconhecimentoFisicoDirectoObjecto.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoReconhecimentoFisicoDirectoObjecto.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoReconhecimentoFisicoDirectoObjecto.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoReconhecimentoFisicoDirectoObjecto.setUser(user);
            }
            autoReconhecimentoFisicoDirectoObjectoRepository.save(autoReconhecimentoFisicoDirectoObjecto);
        }, () -> {
            throw new NotFoundException("Auto de reconhecimento físico directo de objecto não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de reconhecimento físico directo de objecto actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoReconhecimentoFisicoDirectoObjectoDTO>> getAll() {
        List<AutoReconhecimentoFisicoDirectoObjectoDTO> autosReconhecimentoFisicoDirectoObjectos = autoReconhecimentoFisicoDirectoObjectoRepository.findAll()
                .stream().map(autoReconhecimentoFisicoDirectoObjectoMapper::autoReconhecimentoFisicoDirectoObjectoToAutoReconhecimentoFisicoDirectoObjectoDTO)
                .toList();

        return Response.<List<AutoReconhecimentoFisicoDirectoObjectoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosReconhecimentoFisicoDirectoObjectos.isEmpty() ? "Nenhum auto de reconhecimento físico directo de objecto encontrado" : "Autos de reconhecimento físico directo de objectos encontrados com sucesso")
                .data(autosReconhecimentoFisicoDirectoObjectos)
                .build();
    }
}
