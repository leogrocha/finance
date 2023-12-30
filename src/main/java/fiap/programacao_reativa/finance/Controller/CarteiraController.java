package fiap.programacao_reativa.finance.Controller;

import fiap.programacao_reativa.finance.Model.Carteira;
import fiap.programacao_reativa.finance.Request.CarteiraComAcoesRequest;
import fiap.programacao_reativa.finance.Service.CarteiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/carteiras")
public class CarteiraController {

  private final CarteiraService carteiraService;

  @Autowired
  public CarteiraController(CarteiraService carteiraService) {
    this.carteiraService = carteiraService;
  }

  @GetMapping
  public Flux<Carteira> getAll(){
    return carteiraService.findAll();
  }

  @PostMapping
  public Mono<Carteira> save(@RequestBody CarteiraComAcoesRequest carteira) {
    return carteiraService.save(carteira.nomeCarteira, carteira.acoes);
  }

  @GetMapping("/{carteiraNome}/rentabilidade")
  public Mono<ResponseEntity<Double>> calcularRentabilidade(@PathVariable String carteiraNome) {
    return carteiraService.calcularRentabilidade(carteiraNome)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

}
