package pl.example.reactdict.repository.mongo.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.example.reactdict.repository.impl.MongoDictionaryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
@Slf4j
@EnabledIfEnvironmentVariable(named = "repository.type", matches = "mongodb")
public class MongoDictionaryRepositoryTest {

    @Autowired
    MongoDictionaryRepository repository;

    @Test
    public void existsTest() {
        Mono<Boolean> res = repository.contains("abace");
        StepVerifier.create(res)
                .expectNext(Boolean.TRUE).verifyComplete();
    }

    @Test
    public void notExistsTest() {
        Mono<Boolean> res = repository.contains("aaaaaa");
        StepVerifier.create(res)
                .expectNext(Boolean.FALSE).verifyComplete();
    }

    @Test
    public void matchTest() throws InterruptedException {
        String regex = "^d.*ariusz.*";
        Flux<String> result = repository.find(regex);
        StepVerifier.create(result)
                .expectNextCount(84)
                .verifyComplete();
    }

    @Test
    public void testFindingByMultipleRegex() {
        String regexA = "([^a]*a){4}[^a]*";
        String regexT = "([^t]*t){4}[^t]*";
        Flux<String> result = repository.find(List.of(regexA, regexT));
        StepVerifier.create(result)
                .expectNext("antytotalitarna")
                .verifyComplete();
    }
}
