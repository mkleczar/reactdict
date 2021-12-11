package pl.example.reactdict.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DictionaryRepository {
    Flux<String> find(String regex);
    Flux<String> find(List<String> regexes);
    Mono<Boolean> contains(String word);
}
