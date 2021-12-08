package pl.example.reactdict.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DictionaryService {
    // TODO: impl
    Flux<String> wordsFromLetters(String allowedLetters);
    Flux<String> wordsFromLetters(String allowedLetters, int blanksNumber);
    Flux<String> wordsFromRegex(String regex);
    Mono<Boolean> exists(String word);
}
