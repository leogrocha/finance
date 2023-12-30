package fiap.programacao_reativa.finance.Service;


import fiap.programacao_reativa.finance.Model.Acao;
import fiap.programacao_reativa.finance.Model.Carteira;
import fiap.programacao_reativa.finance.Repository.CarteiraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CarteiraService {

  private final CarteiraRepository carteiraRepository;

  @Autowired
  public CarteiraService(CarteiraRepository carteiraRepository) {
    this.carteiraRepository = carteiraRepository;
  }

  public Mono<Double> calcularRentabilidade(String carteiraNome) {
    return carteiraRepository.findByNome(carteiraNome)
        .map(carteira -> carteira.getAcoes().stream()
            .mapToDouble(Acao::getPreco)
            .sum());
  }

  public Flux<Carteira> findAll(){
    return carteiraRepository.findAll();
  }

  public Mono<Carteira> save(String nomeCarteira, List<Acao> acoes) {
    var carteira = new Carteira(nomeCarteira, acoes);
    return carteiraRepository.save(carteira);
  }
}
