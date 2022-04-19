package pl.example.reactdict.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.example.reactdict.model.Blanks;
import pl.example.reactdict.repository.impl.FromFileWordStreamService;
import pl.example.reactdict.repository.impl.WordStreamDictionaryRepository;
import pl.example.reactdict.service.DictionaryService;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Test
    public void asyncTest() throws InterruptedException {
        String letters = "mariusz";
        //Scheduler scheduler = Schedulers.newBoundedElastic(5, 10, "MyBoy");
        Scheduler scheduler = Schedulers.fromExecutorService(Executors.newFixedThreadPool(5));

        service.allPossibleWords("mariusz").delayElements(Duration.ofMillis(200)).subscribe(str -> log.info("Mariusz:  {}", str));
        //service.allPossibleWords("donpedro").subscribe(str -> log.info("DonPedro: {}", str));
        log.info("I am here");
        //Thread.sleep(6_000);
    }

    @Test
    public void asyncMonoTest() throws InterruptedException {
        service.isWordValid("koryto").delayElement(Duration.ofMillis(200)).subscribe(b -> log.info("koryto is valud? :{}", b));
        log.info("I am here");
        Thread.sleep(6_000);
    }
}
