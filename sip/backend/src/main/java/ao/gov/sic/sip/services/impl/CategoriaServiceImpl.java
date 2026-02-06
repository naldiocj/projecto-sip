package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.CategoriaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Categoria;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.CategoriaMapper;
import ao.gov.sic.sip.repositories.CategoriaRepository;
import ao.gov.sic.sip.services.CategoriaService;
import ao.gov.sic.sip.services.DirecaoCSVService;
import ao.gov.sic.sip.services.StorageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final DirecaoCSVService direcaoCSVService;
    private final StorageFileService storageFileService;

    @Override
    public Response<CategoriaDTO> getById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada"));

        CategoriaDTO categoriaDTO = categoriaMapper.categoriaToCategoryDTO(categoria);

        return Response.<CategoriaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Categoria encontrada com sucesso")
                .data(categoriaDTO)
                .build();
    }

    @Override
    public Response<?> create(CategoriaDTO dto) {
        Categoria founded = categoriaRepository.findAllByNome(dto.getNome());
        if (founded != null) {
            throw new RuntimeException("Categoria já existe");
        }

        Categoria categoria = categoriaMapper.categoriaDTOToCategoria(dto);
        categoriaRepository.save(categoria);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Categoria criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NotFoundException("Categoria não encontrada");
        }
        categoriaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Categoria eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(CategoriaDTO dto, Long id) {
        categoriaRepository.findById(id).ifPresentOrElse(categoria -> {
            if (StringUtils.hasText(dto.getNome())) {
                categoria.setNome(dto.getNome());
            }
            categoriaRepository.save(categoria);
        }, () -> {
            throw new NotFoundException("Categoria não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Categoria actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<CategoriaDTO>> getAll() {
        List<CategoriaDTO> categorias = categoriaRepository.findAll()
                .stream().map(categoriaMapper::categoriaToCategoryDTO)
                .toList();

        return Response.<List<CategoriaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(categorias.isEmpty() ? "Nenhuma categoria encontrada" : "Categorias encontradas com sucesso")
                .data(categorias)
                .build();
    }
}
