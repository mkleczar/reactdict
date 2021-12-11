package pl.example.reactdict.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.example.reactdict.repository.impl.FromFileWordStreamService;
import pl.example.reactdict.repository.impl.WordStreamDictionaryRepository;
import pl.example.reactdict.service.DictionaryService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class DictionaryServiceImplTest {

    @Test
    public void scrabbleTest() {
        DictionaryService service = new DictionaryServiceImpl(
                new WordStreamDictionaryRepository(
                        new FromFileWordStreamService("data//slowa.txt")));

        String letters = "abcdefghij";
        Flux<String> result = service.allPossibleWords(letters)
                .filter(str -> str.length() > 6);
        StepVerifier.create(result)
                .expectNext("biedach", "biegach", "dabecji", "dbajcie", "egidach")
                .verifyComplete();
    }
}
