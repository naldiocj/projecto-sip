package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.DespachoDTO;
import ao.gov.sic.sip.entities.Despacho;
import ao.gov.sic.sip.entities.Processo;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.DespachoMapper;
import ao.gov.sic.sip.repositories.DespachoRepository;
import ao.gov.sic.sip.repositories.ProcessoRepository;
import ao.gov.sic.sip.services.DespachoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DespachoServiceImpl implements DespachoService {

    private final DespachoRepository despachoRepository;
    private final DespachoMapper despachoMapper;
    private final ProcessoRepository processoRepository;

    @Override
    @Transactional
    public DespachoDTO create(DespachoDTO dto) {
        Despacho despacho = despachoMapper.toEntity(dto);
        
        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            despacho.setProcesso(processo);
            despacho.setNumeroProcesso(processo.getNumero());
        }

        despacho = despachoRepository.save(despacho);
        return despachoMapper.toDTO(despacho);
    }

    @Override
    @Transactional
    public DespachoDTO update(Long id, DespachoDTO dto) {
        Despacho despacho = despachoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Despacho não encontrado"));

        despacho.setDecisao(dto.getDecisao());
        despacho.setDataDespacho(dto.getDataDespacho());
        despacho.setAutoridadeResponsavel(dto.getAutoridadeResponsavel());
        despacho.setObservacoes(dto.getObservacoes());
        despacho.setFinalizado(dto.isFinalizado());

        if (dto.getProcessoId() != null) {
            Processo processo = processoRepository.findById(dto.getProcessoId())
                    .orElseThrow(() -> new NotFoundException("Processo não encontrado"));
            despacho.setProcesso(processo);
            despacho.setNumeroProcesso(processo.getNumero());
        }

        despacho = despachoRepository.save(despacho);
        return despachoMapper.toDTO(despacho);
    }

    @Override
    public DespachoDTO findById(Long id) {
        return despachoRepository.findById(id)
                .map(despachoMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Despacho não encontrado"));
    }

    @Override
    public List<DespachoDTO> findAll() {
        List<DespachoDTO> despachos = despachoRepository.findAll().stream()
                .map(despachoMapper::toDTO)
                .collect(Collectors.toList());

        return despachos;
    }

    @Override
    public List<DespachoDTO> findByProcessoId(Long processoId) {
        return despachoRepository.findByProcessoId(processoId).stream()
                .map(despachoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!despachoRepository.existsById(id)) {
            throw new NotFoundException("Despacho não encontrado");
        }
        despachoRepository.deleteById(id);
    }
}
