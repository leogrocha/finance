package fiap.programacao_reativa.finance.Repository;

import fiap.programacao_reativa.finance.Model.Carteira;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CarteiraRepository extends ReactiveMongoRepository<Carteira, String> {
  Mono<Carteira> findByNome(String nome);
  Flux<Carteira> findAll();
}
