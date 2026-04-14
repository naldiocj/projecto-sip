package ao.gov.sic.sip.bootstrap;

import ao.gov.sic.sip.entities.Direcao;
import ao.gov.sic.sip.repositories.DirecaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Direcoes {
    private final DirecaoRepository direcaoRepository;

    public void loadData() {
        if (direcaoRepository.count() == 0) {
            List<Direcao> direccoes = Arrays.asList(
                    createDirecao("Direção Geral", "DG"),
                    createDirecao("Direção de Combate ao Narcotráfico", "DCN"),
                    createDirecao("Direção de Crimes Contra a Propriedade", "DCCP"),
                    createDirecao("Direção de Crimes Contra as Pessoas", "DCCP_PES"),
                    createDirecao("Direção de Investigação Criminal", "DIC"),
                    createDirecao("Direção de Inteligência Criminal", "DIN"),
                    createDirecao("Direção de Combate à Corrupção", "DCC"),
                    createDirecao("Direção de Criminalística", "DC"),
                    createDirecao("Direção de Operações", "DO"),
                    createDirecao("Direção de Recursos Humanos", "DRH"),
                    createDirecao("Direção de Finanças", "DF"),
                    createDirecao("Direção de Logística e Património", "DLP"),
                    createDirecao("Direção de Telecomunicações e Tecnologias", "DTTI"),
                    createDirecao("Direção de Intercâmbio e Cooperação", "DICP"),
                    createDirecao("Direção Jurídica e de Estudos", "DJE")
            );

            direcaoRepository.saveAll(direccoes);
            log.info("Bootstrap: {} direções carregadas com sucesso.", direcaoRepository.count());
        }
    }

    private Direcao createDirecao(String nome, String sigla) {
        Direcao direcao = new Direcao();
        direcao.setNome(nome);
        direcao.setSigla(sigla);
        return direcao;
    }
}
