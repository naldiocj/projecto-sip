package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.PatenteDTO;
import ao.gov.sic.sip.dtos.Response;
import ao.gov.sic.sip.mappers.PatenteMapper;
import ao.gov.sic.sip.repositories.PatenteRepository;
import ao.gov.sic.sip.services.PatenteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return null;
    }

    @Override
    public Response<List<PatenteDTO>> getAllPatentes() {
        return null;
    }
}
