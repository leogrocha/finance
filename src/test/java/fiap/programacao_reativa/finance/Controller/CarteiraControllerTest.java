package fiap.programacao_reativa.finance.Controller;

import fiap.programacao_reativa.finance.Model.Acao;
import fiap.programacao_reativa.finance.Model.Carteira;
import fiap.programacao_reativa.finance.Request.CarteiraComAcoesRequest;
import fiap.programacao_reativa.finance.Service.CarteiraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
class CarteiraControllerTest {

  @InjectMocks
  private CarteiraController carteiraController;

  @Mock
  private CarteiraService carteiraService;

  private final Carteira carteira = new Carteira("f-invest", Arrays.asList(
      new Acao("AAPL", 150.0),
      new Acao("GOOGL", 2500.0)
  ));

  final Double rentabilidadeEsperada = 1000.00;
  final String nomeCarteira = "Minha carteira";



  @BeforeEach
  void setUp() {
    BDDMockito.when(carteiraService.findAll())
        .thenReturn(Flux.just(carteira));

    BDDMockito.when(carteiraService.save(carteira.getNome(),
            carteira.getAcoes()))
        .thenReturn(Mono.just(carteira));

    BDDMockito.when(carteiraService.calcularRentabilidade(nomeCarteira))
        .thenReturn(Mono.just(rentabilidadeEsperada));
  }

  @Test
  @DisplayName("buscar todas carteiras salvas")
  void listAll_ReturnFluxOfCarteira_WhenSuccessful() {
    StepVerifier.create(carteiraController.getAll())
        .expectSubscription()
        .expectNext(carteira)
        .verifyComplete();
  }

  @Test
  @DisplayName("Criar carteira")
  void save_CreatesCarteira_WhenSuccessful() {
    CarteiraComAcoesRequest carteiraToBeSaved = new CarteiraComAcoesRequest();
    carteiraToBeSaved.nomeCarteira = carteira.getNome();
    carteiraToBeSaved.acoes = carteira.getAcoes();

    StepVerifier.create(carteiraController.save(carteiraToBeSaved))
        .expectSubscription()
        .expectNext(carteira)
        .verifyComplete();
  }

  @Test
  @DisplayName("Calcular rentabilidade da carteira")
  void calcularRentabilidade_ReturnsRentabilidade_WhenSuccessful() {

    StepVerifier.create(carteiraController.calcularRentabilidade(nomeCarteira))
        .expectSubscription()
        .expectNextMatches(responseEntity -> {
          Double rentabilidade = responseEntity.getBody();
          return responseEntity.getStatusCode().is2xxSuccessful()
              && rentabilidade.equals(rentabilidadeEsperada);
        })
        .verifyComplete();
  }




}
