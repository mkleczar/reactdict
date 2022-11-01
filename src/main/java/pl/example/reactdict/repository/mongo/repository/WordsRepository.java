package pl.example.reactdict.repository.mongo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import pl.example.reactdict.repository.mongo.model.Words;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface WordsRepository extends ReactiveMongoRepository<Words, String> {
    Mono<Words> findByWord(String word);
    Flux<Words> findAllByWordMatchesRegex(String regex);
}
