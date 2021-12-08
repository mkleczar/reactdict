package pl.example.reactdict.repository;

import reactor.core.publisher.Flux;

public interface DictionaryRepository {
    Flux<String> find(String regex);
}
