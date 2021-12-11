package pl.example.reactdict.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.example.reactdict.model.Blanks;
import pl.example.reactdict.repository.impl.FromFileWordStreamService;
import pl.example.reactdict.repository.impl.WordStreamDictionaryRepository;
import pl.example.reactdict.service.DictionaryService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@Slf4j
public class DictionaryServiceImplTest {

    private static DictionaryService service;

    @BeforeAll
    public static void beforeAll() {
        service = new DictionaryServiceImpl(
                new WordStreamDictionaryRepository(
                        new FromFileWordStreamService("data//slowa.txt")));
    }

    @Test
    public void scrabbleTest() {
        String letters = "abcdefghij";
        Flux<String> result = service.allPossibleWords(letters)
                .filter(str -> str.length() > 6);
        StepVerifier.create(result)
                .expectNext("biedach", "biegach", "dabecji", "dbajcie", "egidach")
                .verifyComplete();
    }

    @Test
    public void scrabbleWithBlanksTest() {
        String letters = "jklm";
        int blanks = 2;
        Flux<String> result = service.allPossibleWords(letters, Blanks.TWO)
                .filter(str -> str.length() >= 6);
        StepVerifier.create(result)
                .expectNext("jaklom", "jolkom", "kalmij", "klejem", "klejmy", "klejom", "klujmy", "lajkom", "lejkom")
                .verifyComplete();
    }
}
