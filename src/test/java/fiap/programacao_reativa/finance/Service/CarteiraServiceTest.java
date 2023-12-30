package fiap.programacao_reativa.finance.Service;

import fiap.programacao_reativa.finance.Model.Acao;
import fiap.programacao_reativa.finance.Model.Carteira;
import fiap.programacao_reativa.finance.Repository.CarteiraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarteiraServiceTest {

  final String nomeCarteira = "Minha carteira";

  private final List<Acao> acoes = Arrays.asList(
      new Acao("AAPL", 150.0),
      new Acao("GOOGL", 2500.0)
  );

  @InjectMocks
  private CarteiraService carteiraService;

  @Mock
  private CarteiraRepository carteiraRepository;

  @BeforeEach
  void setUp(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testCalcularRentabilidade(){
    Carteira carteira = new Carteira(nomeCarteira, acoes);

    // mock do comportamento do repository
    Mockito.when(carteiraRepository.findByNome(nomeCarteira)).thenReturn(Mono.just(carteira));

    // Chame o método e verifique o resultado
    StepVerifier.create(carteiraService.calcularRentabilidade(nomeCarteira))
        .expectNext(acoes.stream().mapToDouble(Acao::getPreco).sum()).verifyComplete();
  }

  @Test
  void testFindAll(){
    Carteira carteira1 = new Carteira("Carteira1", Arrays.asList());
    Carteira carteira2 = new Carteira("Carteira2", Arrays.asList());
    List<Carteira> carteiras = Arrays.asList(carteira1, carteira2);

    // mock do repositório
    Mockito.when(carteiraRepository.findAll()).thenReturn(Flux.fromIterable(carteiras));

    // chama o método findAll
    Flux<Carteira> result = carteiraService.findAll();

    // Verifica se o resultado contém as carteiras esperadas
    assertEquals(carteiras, result.collectList().block());
  }

  @Test
  void testSave(){
    Carteira carteira = new Carteira(nomeCarteira, acoes);

    Acao acao1 = new Acao("AAPL", 150.0);
    Acao acao2 = new Acao("GOOGL", 2500.0);
    carteira.setAcoes(Arrays.asList(acao1, acao2));

    Mockito.when(carteiraRepository.save(Mockito.any(Carteira.class))).thenReturn(Mono.just(carteira));

    StepVerifier.create(carteiraService.save(nomeCarteira, carteira.getAcoes()))
        .expectNext(carteira)
        .verifyComplete();
  }

}
