package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.TermoEntregaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Endereco;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.entities.TermoEntrega;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.TermoEntregaMapper;
import ao.gov.sic.sip.repositories.EnderecoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.repositories.TermoEntregaRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.TermoEntregaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TermoEntregaServiceImpl implements TermoEntregaService {
    private final TermoEntregaRepository termoEntregaRepository;
    private final TermoEntregaMapper termoEntregaMapper;
    private final EnderecoRepository enderecoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<TermoEntregaDTO> getById(Long id) {
        TermoEntrega termoEntrega = termoEntregaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Termo de entrega não encontrado"));

        TermoEntregaDTO termoEntregaDTO = termoEntregaMapper.termoEntregaToTermoEntregaDTO(termoEntrega);

        return Response.<TermoEntregaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Termo de entrega encontrado com sucesso")
                .data(termoEntregaDTO)
                .build();
    }

    @Override
    public Response<?> create(TermoEntregaDTO dto) {
        TermoEntrega termoEntrega = termoEntregaMapper.termoEntregaDTOToTermoEntrega(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            termoEntrega.setEndereco(endereco);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            termoEntrega.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            termoEntrega.setUser(user);
        }

        termoEntregaRepository.save(termoEntrega);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Termo de entrega criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!termoEntregaRepository.existsById(id)) {
            throw new NotFoundException("Termo de entrega não encontrado");
        }
        termoEntregaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Termo de entrega eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(TermoEntregaDTO dto, Long id) {
        termoEntregaRepository.findById(id).ifPresentOrElse(termoEntrega -> {
            if (dto.getNumeroFolha() != null) {
                termoEntrega.setNumeroFolha(dto.getNumeroFolha());
            }
            if (dto.getVistoDirector() != null) {
                termoEntrega.setVistoDirector(dto.getVistoDirector());
            }
            if (dto.getDataEmissao() != null) {
                termoEntrega.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDespachoMagistrado())) {
                termoEntrega.setDespachoMagistrado(dto.getDespachoMagistrado());
            }
            if (StringUtils.hasText(dto.getArtigoApreendido())) {
                termoEntrega.setArtigoApreendido(dto.getArtigoApreendido());
            }
            if (dto.getDataEntrega() != null) {
                termoEntrega.setDataEntrega(dto.getDataEntrega());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                termoEntrega.setEndereco(endereco);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                termoEntrega.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                termoEntrega.setUser(user);
            }
            termoEntregaRepository.save(termoEntrega);
        }, () -> {
            throw new NotFoundException("Termo de entrega não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Termo de entrega actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<TermoEntregaDTO>> getAll() {
        List<TermoEntregaDTO> termosEntregas = termoEntregaRepository.findAll()
                .stream().map(termoEntregaMapper::termoEntregaToTermoEntregaDTO)
                .toList();

        return Response.<List<TermoEntregaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(termosEntregas.isEmpty() ? "Nenhum termo de entrega encontrado" : "Termos de entrega encontrados com sucesso")
                .data(termosEntregas)
                .build();
    }
}
