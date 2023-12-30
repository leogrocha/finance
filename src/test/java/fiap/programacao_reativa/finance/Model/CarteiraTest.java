package fiap.programacao_reativa.finance.Model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarteiraTest {

  @Test
  void adicionarAcao_DeveAdicionarAcaoNaCarteira(){
    // ARRANGE
    List<Acao> acoes = Arrays.asList(
        new Acao("AAPL", 150.0),
        new Acao("GOOGL", 2500.00)
    );

    Carteira carteira = new Carteira("Minha carteira", acoes);

    // ACT
    Acao novaAcao = new Acao("TSLA", 700.00);
    carteira.adicionarAcao(novaAcao);

    // ASSERT
    assertEquals(3, carteira.getAcoes().size());
  }

}
