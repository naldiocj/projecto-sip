package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.*;
import ao.gov.sic.sip.enums.EstadoProcesso;
import ao.gov.sic.sip.enums.TipoProcesso;
import ao.gov.sic.sip.exceptions.NotFoundException;
import ao.gov.sic.sip.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Processos {
    private final DirectorRepository directorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DirecaoRepository direcaoRepository;
    private final ProcessoRepository processoRepository;
    private final TipoCrimeRepository tipoCrimeRepository;

    public void loadData() {
        loadProcessos();
    }

    private void loadProcessos() {
        if (processoRepository.findAll().isEmpty()) {
            User secretariaGeral = userRepository.findByEmail("secretaria.geral@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));
            User secretariaDCN = userRepository.findByEmail("secretaria.dcn@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));
            User secretariaDTTI = userRepository.findByEmail("secretaria.dtti@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));
            User secretariaRH = userRepository.findByEmail("secretaria.rh@sic.gov.ao")
                    .orElseThrow(() -> new NotFoundException("User not found"));

            Direcao direcao1 = direcaoRepository.findById(2L).orElseThrow(() -> new NotFoundException("Direcao not found"));
            Direcao direcao2 = direcaoRepository.findById(13L).orElseThrow(() -> new NotFoundException("Direcao not found"));
            Direcao direcao3 = direcaoRepository.findById(10L).orElseThrow(() -> new NotFoundException("Direcao not found"));


            TipoCrime crime1 = tipoCrimeRepository.findById(1L).orElseThrow(() -> new NotFoundException("Tipo crime not found"));
            TipoCrime crime2 = tipoCrimeRepository.findById(2L).orElseThrow(() -> new NotFoundException("Tipo crime not found"));
            TipoCrime crime3 = tipoCrimeRepository.findById(3L).orElseThrow(() -> new NotFoundException("Tipo crime not found"));
            TipoCrime crime4 = tipoCrimeRepository.findById(4L).orElseThrow(() -> new NotFoundException("Tipo crime not found"));
            TipoCrime crime5 = tipoCrimeRepository.findById(5L).orElseThrow(() -> new NotFoundException("Tipo crime not found"));
            TipoCrime crime6 = tipoCrimeRepository.findById(6L).orElseThrow(() -> new NotFoundException("Tipo crime not found"));

            // Processos DCN
            Processo processo1 = Processo.builder()
                    .ano(2021)
                    .estadoProcesso(EstadoProcesso.EM_INSTRUCAO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("001/2024-PGR-CN")
                    .descricao("Processo CT.")
                    .crimes(Set.of(crime1, crime2))
                    .direcao(direcao1)
                    .user(secretariaGeral)
                    .build();

            Processo processo2 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.REMETIDO_JUIZO)
                    .tipoProcesso(TipoProcesso.COM_DETIDO)
                    .numero("002/2024-PGR-CN")
                    .descricao("Processo de averiguação imediata.")
                    .crimes(Set.of(crime2, crime3))
                    .direcao(direcao1)
                    .user(secretariaGeral)
                    .build();

            Processo processo3 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.ARQUIVADO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("003/2024-PGR-CN")
                    .descricao("Processo aguardando provas periciais.")
                    .crimes(Set.of(crime4))
                    .direcao(direcao1)
                    .user(secretariaGeral)
                    .build();

            Processo processo4 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.ARQUIVADO)
                    .tipoProcesso(TipoProcesso.AVERIGUACAO)
                    .numero("004/2024-PGR-CN")
                    .descricao("Processo de natureza reservada.")
                    .crimes(Set.of(crime4, crime5))
                    .direcao(direcao1)
                    .user(secretariaGeral)
                    .build();

            Processo processo5 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.SENTENCIADO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("005/2024-PGR-CN")
                    .descricao("Análise de conformidade administrativa.")
                    .crimes(Set.of(crime6))
                    .direcao(direcao1)
                    .user(secretariaGeral)
                    .build();

            Processo processo6 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.REMETIDO_PGR)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("006/2024-PGR-CN")
                    .descricao("Análise de conformidade administrativa.")
                    .crimes(Set.of(crime3))
                    .direcao(direcao1)
                    .user(secretariaGeral)
                    .build();

            processoRepository.saveAll(List.of(processo1, processo2, processo3, processo4, processo5, processo6));

            // Processos DTTI
            Processo processo21 = Processo.builder()
                    .ano(2021)
                    .estadoProcesso(EstadoProcesso.EM_INSTRUCAO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("001/2024-PGR-TI")
                    .descricao("Processo CT.")
                    .crimes(Set.of(crime4, crime1))
                    .direcao(direcao2)
                    .user(secretariaGeral)
                    .build();

            Processo processo22 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.REMETIDO_JUIZO)
                    .tipoProcesso(TipoProcesso.COM_DETIDO)
                    .numero("002/2024-PGR-TI")
                    .descricao("Processo de averiguação imediata.")
                    .crimes(Set.of(crime5, crime1))
                    .direcao(direcao2)
                    .user(secretariaGeral)
                    .build();

            Processo processo23 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.ARQUIVADO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("003/2024-PGR-TI")
                    .descricao("Processo aguardando provas periciais.")
                    .crimes(Set.of(crime2))
                    .direcao(direcao2)
                    .user(secretariaGeral)
                    .build();

            Processo processo24 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.ARQUIVADO)
                    .tipoProcesso(TipoProcesso.AVERIGUACAO)
                    .numero("004/2024-PGR-TI")
                    .descricao("Processo de natureza reservada.")
                    .crimes(Set.of(crime3))
                    .direcao(direcao2)
                    .user(secretariaGeral)
                    .build();

            Processo processo25 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.SENTENCIADO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("005/2024-PGR-TI")
                    .descricao("Análise de conformidade administrativa.")
                    .crimes(Set.of(crime1, crime2))
                    .direcao(direcao2)
                    .user(secretariaGeral)
                    .build();

            Processo processo26 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.REMETIDO_PGR)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("006/2024-PGR-TI")
                    .descricao("Análise de conformidade administrativa.")
                    .crimes(Set.of(crime3, crime2))
                    .direcao(direcao2)
                    .user(secretariaGeral)
                    .build();

            List<Processo> processosDTTI = processoRepository.saveAll(List.of(processo21, processo22, processo23, processo24, processo25, processo26));


            // Processos RH
            Processo processo31 = Processo.builder()
                    .ano(2021)
                    .estadoProcesso(EstadoProcesso.EM_INSTRUCAO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("001/2024-PGR-RH")
                    .descricao("Processo CT.")
                    .crimes(Set.of(crime3))
                    .direcao(direcao3)
                    .user(secretariaGeral)
                    .build();

            Processo processo32 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.REMETIDO_JUIZO)
                    .tipoProcesso(TipoProcesso.COM_DETIDO)
                    .numero("002/2024-PGR-RH")
                    .descricao("Processo de averiguação imediata.")
                    .crimes(Set.of(crime4, crime1))
                    .direcao(direcao3)
                    .user(secretariaGeral)
                    .build();

            Processo processo33 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.ARQUIVADO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("003/2024-PGR-RH")
                    .descricao("Processo aguardando provas periciais.")
                    .crimes(Set.of(crime4, crime1))
                    .direcao(direcao3)
                    .user(secretariaGeral)
                    .build();

            Processo processo34 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.ARQUIVADO)
                    .tipoProcesso(TipoProcesso.AVERIGUACAO)
                    .numero("004/2024-PGR-RH")
                    .descricao("Processo de natureza reservada.")
                    .crimes(Set.of(crime3, crime5))
                    .direcao(direcao3)
                    .user(secretariaGeral)
                    .build();

            Processo processo35 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.SENTENCIADO)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("005/2024-PGR-RH")
                    .descricao("Análise de conformidade administrativa.")
                    .crimes(Set.of(crime2))
                    .direcao(direcao3)
                    .user(secretariaGeral)
                    .build();

            Processo processo36 = Processo.builder()
                    .ano(2024)
                    .estadoProcesso(EstadoProcesso.REMETIDO_PGR)
                    .tipoProcesso(TipoProcesso.NORMAL)
                    .numero("006/2024-PGR-RH")
                    .descricao("Análise de conformidade administrativa.")
                    .crimes(Set.of(crime1, crime5))
                    .direcao(direcao3)
                    .user(secretariaGeral)
                    .build();

            processoRepository.saveAll(List.of(processo31, processo32, processo33, processo34, processo35, processo36));
        }
    }
}
