package pl.example.reactdict.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DictionaryService {
    Flux<String> allPossibleWords(String letters);
    Flux<String> allPossibleWords(String allowedLetters, int blanksNumber);
    Flux<String> wordsFromRegex(String regex);
    Mono<Boolean> isWordValid(String word);
}
