package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.AutoCorpoDelitoIndirectoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.AutoCorpoDelitoIndirectoMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.AutoCorpoDelitoIndirectoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutoCorpoDelitoIndirectoServiceImpl implements AutoCorpoDelitoIndirectoService {
    private final AutoCorpoDelitoIndirectoRepository autoCorpoDelitoIndirectoRepository;
    private final AutoCorpoDelitoIndirectoMapper autoCorpoDelitoIndirectoMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;
    private final TestemunhaRepository testemunhaRepository;

    @Override
    public Response<AutoCorpoDelitoIndirectoDTO> getById(Long id) {
        AutoCorpoDelitoIndirecto autoCorpoDelitoIndirecto = autoCorpoDelitoIndirectoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Auto de corpo de delito indirecto não encontrado"));

        AutoCorpoDelitoIndirectoDTO autoCorpoDelitoIndirectoDTO = autoCorpoDelitoIndirectoMapper.autoCorpoDelitoIndirectoToAutoCorpoDelitoIndirectoDTO(autoCorpoDelitoIndirecto);

        return Response.<AutoCorpoDelitoIndirectoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de corpo de delito indirecto encontrado com sucesso")
                .data(autoCorpoDelitoIndirectoDTO)
                .build();
    }

    @Override
    public Response<?> create(AutoCorpoDelitoIndirectoDTO dto) {
        AutoCorpoDelitoIndirecto autoCorpoDelitoIndirecto = autoCorpoDelitoIndirectoMapper.autoCorpoDelitoIndirectoDTOToAutoCorpoDelitoIndirecto(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            autoCorpoDelitoIndirecto.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            autoCorpoDelitoIndirecto.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            autoCorpoDelitoIndirecto.setUser(user);
        }

        if (dto.getTestemunhasIds() != null && !dto.getTestemunhasIds().isEmpty()) {
            List<Testemunha> testemunhas = testemunhaRepository.findAllById(dto.getTestemunhasIds());
            autoCorpoDelitoIndirecto.setTestemunhas(testemunhas);
        }

        autoCorpoDelitoIndirectoRepository.save(autoCorpoDelitoIndirecto);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Auto de corpo de delito indirecto criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!autoCorpoDelitoIndirectoRepository.existsById(id)) {
            throw new NotFoundException("Auto de corpo de delito indirecto não encontrado");
        }
        autoCorpoDelitoIndirectoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de corpo de delito indirecto eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(AutoCorpoDelitoIndirectoDTO dto, Long id) {
        autoCorpoDelitoIndirectoRepository.findById(id).ifPresentOrElse(autoCorpoDelitoIndirecto -> {
            if (dto.getNumeroFolha() != null) {
                autoCorpoDelitoIndirecto.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getDataEmissao() != null) {
                autoCorpoDelitoIndirecto.setDataEmissao(dto.getDataEmissao());
            }
            if (dto.getNaQualidadeDe() != null) {
                autoCorpoDelitoIndirecto.setNaQualidadeDe(dto.getNaQualidadeDe());
            }
            if (StringUtils.hasText(dto.getNaturezaCaracteristicas())) {
                autoCorpoDelitoIndirecto.setNaturezaCaracteristicas(dto.getNaturezaCaracteristicas());
            }
            if (StringUtils.hasText(dto.getMateriaAutos())) {
                autoCorpoDelitoIndirecto.setMateriaAutos(dto.getMateriaAutos());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                autoCorpoDelitoIndirecto.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                autoCorpoDelitoIndirecto.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                autoCorpoDelitoIndirecto.setUser(user);
            }
            if (dto.getTestemunhasIds() != null) {
                List<Testemunha> testemunhas = testemunhaRepository.findAllById(dto.getTestemunhasIds());
                autoCorpoDelitoIndirecto.setTestemunhas(testemunhas);
            }
            autoCorpoDelitoIndirectoRepository.save(autoCorpoDelitoIndirecto);
        }, () -> {
            throw new NotFoundException("Auto de corpo de delito indirecto não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Auto de corpo de delito indirecto actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<AutoCorpoDelitoIndirectoDTO>> getAll() {
        List<AutoCorpoDelitoIndirectoDTO> autosCorpoDelitosIndirectos = autoCorpoDelitoIndirectoRepository.findAll()
                .stream().map(autoCorpoDelitoIndirectoMapper::autoCorpoDelitoIndirectoToAutoCorpoDelitoIndirectoDTO)
                .toList();

        return Response.<List<AutoCorpoDelitoIndirectoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(autosCorpoDelitosIndirectos.isEmpty() ? "Nenhum auto de corpo de delito indirecto encontrado" : "Autos de corpo de delitos indirectos encontrados com sucesso")
                .data(autosCorpoDelitosIndirectos)
                .build();
    }
}
