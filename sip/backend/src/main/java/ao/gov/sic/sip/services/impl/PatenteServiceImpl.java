package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.PatenteDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.PatenteMapper;
import ao.gov.sic.sip.repositories.PatenteRepository;
import ao.gov.sic.sip.services.PatenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatenteServiceImpl implements PatenteService {
    private final PatenteRepository patenteRepository;
    private final PatenteMapper patenteMapper;

    @Override
    public Response<PatenteDTO> getPatenteById(Long id) {
        Patente patent = patenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patente não encontrada"));

        PatenteDTO patentDTO = patenteMapper.patenteToPatenteDTO(patent);

        return Response.<PatenteDTO>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Patente encontrada com sucesso")
                .data(patentDTO)
                .build();
    }

    @Override
    public Response<?> deletePatenteById(Long id) {
        if (!patenteRepository.existsById(id)) {
            throw new NotFoundException("Patente não encontrada");
        }
        patenteRepository.deleteById(id);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Patente eliminada com sucesso")
                .build();
    }

    @Override
    public Response<List<PatenteDTO>> getAllPatentes() {
        List<PatenteDTO> patentes = patenteRepository.findAll()
                .stream().map(patenteMapper::patenteToPatenteDTO)
                .toList();

        return Response.<List<PatenteDTO>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(patentes.isEmpty() ? "Nenhuma patente encontrada" : "Patentes encontradas com sucesso")
                .data(patentes)
                .build();
    }
}
