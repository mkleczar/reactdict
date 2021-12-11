package pl.example.reactdict.service;

import pl.example.reactdict.model.Blanks;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DictionaryService {
    Flux<String> allPossibleWords(String letters);
    Flux<String> allPossibleWords(String allowedLetters, Blanks blanks);
    Flux<String> wordsFromRegex(String regex);
    Mono<Boolean> isWordValid(String word);
}
