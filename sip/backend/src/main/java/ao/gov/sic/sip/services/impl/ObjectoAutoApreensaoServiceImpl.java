package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.ObjectoAutoApreensaoDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.ObjectoAutoApreensao;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.ObjectoAutoApreensaoMapper;
import ao.gov.sic.sip.repositories.ObjectoAutoApreensaoRepository;
import ao.gov.sic.sip.services.ObjectoAutoApreensaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ObjectoAutoApreensaoServiceImpl implements ObjectoAutoApreensaoService {
    private final ObjectoAutoApreensaoRepository objectoAutoApreensaoRepository;
    private final ObjectoAutoApreensaoMapper objectoAutoApreensaoMapper;

    @Override
    public Response<ObjectoAutoApreensaoDTO> getById(Long id) {
        ObjectoAutoApreensao objectoAutoApreensao = objectoAutoApreensaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Objecto de auto de apreensão não encontrado"));

        ObjectoAutoApreensaoDTO objectoAutoApreensaoDTO = objectoAutoApreensaoMapper.objectoAutoApreensaoToObjectoAutoApreensaoDTO(objectoAutoApreensao);

        return Response.<ObjectoAutoApreensaoDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Objecto de auto de apreensão encontrado com sucesso")
                .data(objectoAutoApreensaoDTO)
                .build();
    }

    @Override
    public Response<?> create(ObjectoAutoApreensaoDTO dto) {
        ObjectoAutoApreensao founded = objectoAutoApreensaoRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Objecto de auto de apreensão já existe");
        }

        ObjectoAutoApreensao objectoAutoApreensao = objectoAutoApreensaoMapper.objectoAutoApreensaoDTOToObjectoAutoApreensao(dto);
        objectoAutoApreensaoRepository.save(objectoAutoApreensao);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Objecto de auto de apreensão criado com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!objectoAutoApreensaoRepository.existsById(id)) {
            throw new NotFoundException("Objecto de auto de apreensão não encontrado");
        }
        objectoAutoApreensaoRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Objecto de auto de apreensão eliminado com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(ObjectoAutoApreensaoDTO dto, Long id) {
        objectoAutoApreensaoRepository.findById(id).ifPresentOrElse(objectoAutoApreensao -> {
            if (StringUtils.hasText(dto.getNome())) {
                objectoAutoApreensao.setNome(dto.getNome());
            }
            objectoAutoApreensaoRepository.save(objectoAutoApreensao);
        }, () -> {
            throw new NotFoundException("Objecto de auto de apreensão não encontrado");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Objecto de auto de apreensão actualizado com sucesso")
                .build();
    }

    @Override
    public Response<List<ObjectoAutoApreensaoDTO>> getAll() {
        List<ObjectoAutoApreensaoDTO> objectosAutosApreensoes = objectoAutoApreensaoRepository.findAll()
                .stream().map(objectoAutoApreensaoMapper::objectoAutoApreensaoToObjectoAutoApreensaoDTO)
                .toList();

        return Response.<List<ObjectoAutoApreensaoDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(objectosAutosApreensoes.isEmpty() ? "Nenhum objecto de auto de apreensão encontrado" : "Objectos de autos de apreensões encontrados com sucesso")
                .data(objectosAutosApreensoes)
                .build();
    }
}
