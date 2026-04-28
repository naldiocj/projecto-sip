package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.DetidoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Detido;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DetidoMapper;
import ao.gov.sic.sip.repositories.DetidoRepository;
import ao.gov.sic.sip.services.DetidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetidoServiceImpl implements DetidoService {

    private final DetidoRepository detidoRepository;
    private final DetidoMapper detidoMapper;

    @Override
    @Transactional
    public Response<DetidoDTO> create(DetidoDTO dto) {
        Detido detido = detidoMapper.toEntity(dto);
        detido = detidoRepository.save(detido);
        
        return Response.<DetidoDTO>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Detido registado com sucesso")
                .data(detidoMapper.toDTO(detido))
                .build();
    }

    @Override
    @Transactional
    public Response<DetidoDTO> update(Long id, DetidoDTO dto) {
        Detido detido = detidoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Detido não encontrado"));

        detido.setNumeroProcesso(dto.getNumeroProcesso());
        detido.setNomeCompleto(dto.getNomeCompleto());
        detido.setNomePai(dto.getNomePai());
        detido.setNomeMae(dto.getNomeMae());
        detido.setDataNascimento(dto.getDataNascimento());
        detido.setNaturalidade(dto.getNaturalidade());
        detido.setProfissao(dto.getProfissao());
        detido.setNumeroBi(dto.getNumeroBi());
        detido.setDataEmissaoBi(dto.getDataEmissaoBi());
        detido.setLocalEmissaoBi(dto.getLocalEmissaoBi());
        detido.setTelefone(dto.getTelefone());
        detido.setEndereco(dto.getEndereco());
        detido.setEstadoCivil(dto.getEstadoCivil());
        detido.setIdade(dto.getIdade());
        detido.setSexo(dto.getSexo());
        detido.setEstadoDetencao(dto.getEstadoDetencao());
        detido.setDataDetencao(dto.getDataDetencao());
        detido.setLocalDetencao(dto.getLocalDetencao());
        detido.setMotivoDetencao(dto.getMotivoDetencao());
        detido.setObservacoes(dto.getObservacoes());

        detido = detidoRepository.save(detido);
        
        return Response.<DetidoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Dados do detido actualizados com sucesso")
                .data(detidoMapper.toDTO(detido))
                .build();
    }

    @Override
    public Response<DetidoDTO> findById(Long id) {
        DetidoDTO dto = detidoRepository.findById(id)
                .map(detidoMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Detido não encontrado"));

        return Response.<DetidoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Detido encontrado")
                .data(dto)
                .build();
    }

    @Override
    public Response<List<DetidoDTO>> findAll() {
        List<DetidoDTO> dtos = detidoRepository.findAll().stream()
                .map(detidoMapper::toDTO)
                .collect(Collectors.toList());

        return Response.<List<DetidoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(dtos.isEmpty() ? "Nenhum detido encontrado" : "Detidos encontrados")
                .data(dtos)
                .build();
    }

    @Override
    @Transactional
    public Response<Void> delete(Long id) {
        if (!detidoRepository.existsById(id)) {
            throw new NotFoundException("Detido não encontrado");
        }
        detidoRepository.deleteById(id);

        return Response.<Void>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Registo do detido eliminado com sucesso")
                .build();
    }
}
