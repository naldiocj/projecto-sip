package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.CartaPrecatoriaDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.CartaPrecatoriaMapper;
import ao.gov.sic.sip.repositories.*;
import ao.gov.sic.sip.services.CartaPrecatoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaPrecatoriaServiceImpl implements CartaPrecatoriaService {
    private final CartaPrecatoriaRepository cartaPrecatoriaRepository;
    private final CartaPrecatoriaMapper cartaPrecatoriaMapper;
    private final EnderecoRepository enderecoRepository;
    private final LivroRegistoRepository livroRegistoRepository;
    private final ProcessoRepository processoRepository;
    private final UserRepository userRepository;

    @Override
    public Response<CartaPrecatoriaDTO> getById(Long id) {
        CartaPrecatoria cartaPrecatoria = cartaPrecatoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Carta precatória não encontrada"));

        CartaPrecatoriaDTO cartaPrecatoriaDTO = cartaPrecatoriaMapper.cartaPrecatoriaToCartaPrecatoriaDTO(cartaPrecatoria);

        return Response.<CartaPrecatoriaDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Carta precatória encontrada com sucesso")
                .data(cartaPrecatoriaDTO)
                .build();
    }

    @Override
    public Response<?> create(CartaPrecatoriaDTO dto) {
        CartaPrecatoria founded = cartaPrecatoriaRepository.findAllByNumeroCarta(dto.getNumeroCarta());
        if (founded != null) {
            throw new RuntimeException("Carta precatória já existe");
        }

        CartaPrecatoria cartaPrecatoria = cartaPrecatoriaMapper.cartaPrecatoriaDTOToCartaPrecatoria(dto);

        if (dto.getEnderecoId() != null) {
            Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                    .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
            cartaPrecatoria.setEndereco(endereco);
        }

        if (dto.getLivroRegistoId() != null) {
            LivroRegisto livroRegisto = livroRegistoRepository.findById(dto.getLivroRegistoId())
                    .orElseThrow(() -> new NotFoundException("Livro de registo não encontrado"));
            cartaPrecatoria.setLivroRegisto(livroRegisto);
        }

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            cartaPrecatoria.setProcesso(processo);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            cartaPrecatoria.setUser(user);
        }

        cartaPrecatoriaRepository.save(cartaPrecatoria);

        return Response.builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Carta precatória criada com sucesso")
                .build();
    }

    @Override
    public Response<?> deleteById(Long id) {
        if (!cartaPrecatoriaRepository.existsById(id)) {
            throw new NotFoundException("Carta precatória não encontrada");
        }
        cartaPrecatoriaRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Carta precatória eliminada com sucesso")
                .build();
    }

    @Override
    public Response<?> updateById(CartaPrecatoriaDTO dto, Long id) {
        cartaPrecatoriaRepository.findById(id).ifPresentOrElse(cartaPrecatoria -> {
            if (StringUtils.hasText(dto.getNumeroCarta())) {
                cartaPrecatoria.setNumeroCarta(dto.getNumeroCarta());
            }
            if (dto.getDataEmissao() != null) {
                cartaPrecatoria.setDataEmissao(dto.getDataEmissao());
            }
            if (StringUtils.hasText(dto.getDeprecante())) {
                cartaPrecatoria.setDeprecante(dto.getDeprecante());
            }
            if (StringUtils.hasText(dto.getDescricao())) {
                cartaPrecatoria.setDescricao(dto.getDescricao());
            }
            if (dto.getEnderecoId() != null) {
                Endereco endereco = enderecoRepository.findById(dto.getEnderecoId())
                        .orElseThrow(() -> new NotFoundException("Endereço não encontrado"));
                cartaPrecatoria.setEndereco(endereco);
            }
            if (dto.getLivroRegistoId() != null) {
                LivroRegisto livroRegisto = livroRegistoRepository.findById(dto.getLivroRegistoId())
                        .orElseThrow(() -> new NotFoundException("Livro de registo não encontrado"));
                cartaPrecatoria.setLivroRegisto(livroRegisto);
            }
            if (dto.getProcessoId() != null) {
                Processo processo = processoRepository.findById(dto.getProcessoId())
                        .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
                cartaPrecatoria.setProcesso(processo);
            }
            if (dto.getUserId() != null) {
                User user = userRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
                cartaPrecatoria.setUser(user);
            }
            cartaPrecatoriaRepository.save(cartaPrecatoria);
        }, () -> {
            throw new NotFoundException("Carta precatória não encontrada");
        });

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Carta precatória actualizada com sucesso")
                .build();
    }

    @Override
    public Response<List<CartaPrecatoriaDTO>> getAll() {
        List<CartaPrecatoriaDTO> cartasPrecatorias = cartaPrecatoriaRepository.findAll()
                .stream().map(cartaPrecatoriaMapper::cartaPrecatoriaToCartaPrecatoriaDTO)
                .toList();

        return Response.<List<CartaPrecatoriaDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(cartasPrecatorias.isEmpty() ? "Nenhuma carta precatória encontrada" : "Cartas precatórias encontradas com sucesso")
                .data(cartasPrecatorias)
                .build();
    }
}
