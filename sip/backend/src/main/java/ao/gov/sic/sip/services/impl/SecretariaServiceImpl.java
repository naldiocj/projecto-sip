package ao.gov.sic.sip.services.impl;

import ao.gov.sic.sip.dtos.SecretariaDTO;
import ao.gov.sic.sip.entities.Direcao;
import ao.gov.sic.sip.entities.Patente;
import ao.gov.sic.sip.entities.Secretaria;
import ao.gov.sic.sip.entities.User;
import ao.gov.sic.sip.enums.SecretariaType;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.mappers.SecretariaMapper;
import ao.gov.sic.sip.repositories.DirecaoRepository;
import ao.gov.sic.sip.repositories.PatenteRepository;
import ao.gov.sic.sip.repositories.SecretariaRepository;
import ao.gov.sic.sip.repositories.UserRepository;
import ao.gov.sic.sip.services.SecretariaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecretariaServiceImpl implements SecretariaService {

    private final SecretariaRepository secretariaRepository;
    private final SecretariaMapper secretariaMapper;
    private final PatenteRepository patenteRepository;
    private final DirecaoRepository direcaoRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public SecretariaDTO create(SecretariaDTO dto) {
        Secretaria secretaria = secretariaMapper.toEntity(dto);

        if (dto.getType() == null) {
            secretaria.setType(SecretariaType.ORGAO);
        } else {
            secretaria.setType(dto.getType());
        }
        
        if (dto.getPatenteId() != null) {
            Patente patente = patenteRepository.findById(dto.getPatenteId())
                    .orElseThrow(() -> new NotFoundException("Patente não encontrada"));
            secretaria.setPatente(patente);
        }

        if (dto.getDirecaoId() != null) {
            Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                    .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
            secretaria.setDirecao(direcao);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            secretaria.setUser(user);
        }

        secretaria = secretariaRepository.save(secretaria);
        return secretariaMapper.toDTO(secretaria);
    }

    @Override
    @Transactional
    public SecretariaDTO update(Long id, SecretariaDTO dto) {
        Secretaria secretaria = secretariaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Secretaria não encontrada"));

        secretaria.setNomeCompleto(dto.getNomeCompleto());
        
        if (dto.getType() != null) {
            secretaria.setType(dto.getType());
        }

        if (dto.getPatenteId() != null) {
            Patente patente = patenteRepository.findById(dto.getPatenteId())
                    .orElseThrow(() -> new NotFoundException("Patente não encontrada"));
            secretaria.setPatente(patente);
        }

        if (dto.getDirecaoId() != null) {
            Direcao direcao = direcaoRepository.findById(dto.getDirecaoId())
                    .orElseThrow(() -> new NotFoundException("Direção não encontrada"));
            secretaria.setDirecao(direcao);
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
            secretaria.setUser(user);
        }

        secretaria = secretariaRepository.save(secretaria);
        return secretariaMapper.toDTO(secretaria);
    }

    @Override
    public SecretariaDTO findById(Long id) {
        return secretariaRepository.findById(id)
                .map(secretariaMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Secretaria não encontrada"));
    }

    @Override
    public List<SecretariaDTO> findAll() {
        return secretariaRepository.findAll().stream()
                .map(secretariaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!secretariaRepository.existsById(id)) {
            throw new NotFoundException("Secretaria não encontrada");
        }
        secretariaRepository.deleteById(id);
    }
}
