package fiap.programacao_reativa.finance.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AcaoTest {

  @Test
  void criarAcao_DeveDefinirSimboloEPrecoCorretamente(){
    // ARRANGE
    String simbolo = "AAPL";
    double preco = 150.0;

    // ACT
    Acao acao = new Acao(simbolo, preco);

    // ASSERT
    assertEquals(simbolo, acao.getSimbolo());
    assertEquals(preco, acao.getPreco(), 0.01);

  }
}
