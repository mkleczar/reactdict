package pl.example.reactdict.repository.mongo.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import pl.example.reactdict.repository.mongo.model.Words;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

@Slf4j
@SpringBootTest
@EnabledIfEnvironmentVariable(named = "repository.type", matches = "mongodb")
public class DictionaryTest {

    @Autowired
    WordsRepository repository;

    @Test
    public void mongoRepositoryCountTest() {
        Long count = repository.count().block();
        log.info("Liczba rekordów: {}", count);
    }

    @Test
    public void mongoTest() throws InterruptedException {
        repository.findAll().subscribe(w -> log.info("{}", w));
        Thread.sleep(2000);
    }

    @Test
    public void mongoRegex() {
        Long count = repository.findAllByWordMatchesRegex("^skór").count().block();
        log.info("Liczba rekordów: {}", count);
    }

    @Test
    public void testFindingWordsByRegex() {
        String regex = "^d.*ariusz.*";
        Flux<Words> result = repository.findAllByWordMatchesRegex(regex).log();
        StepVerifier.create(result)
                .expectNextCount(84)
                .verifyComplete();
    }

    @Test
    public void testFindingWordsByRegex2() {
        String regex = "([^d]*d){3,5}[^d]*";
        List<Words> result = repository.findAllByWordMatchesRegex(regex)
                .log()
                .doOnNext(w -> log.info("{}", w.getWord()))
                .collectList()
                .block();
    }

    @Test
    public void testFindingWordsByRegex3() {
        String regexM = "([^m]*m){0,2}[^m]*";
        String regexA = "([^a]*a){0,2}[^a]*";
        String regex = "(?=" + regexM +")(?=" + regexA + ")(?=^[mmaa]{1,4}$)";
        log.info("regex: {}", regex);
        List<Words> result = repository.findAllByWordMatchesRegex(regex)
                .log()
                .doOnNext(w -> log.info("{}", w.getWord()))
                .collectList()
                .block();
    }
}
