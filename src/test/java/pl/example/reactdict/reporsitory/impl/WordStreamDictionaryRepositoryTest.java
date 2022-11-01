package pl.example.reactdict.reporsitory.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import pl.example.reactdict.repository.DictionaryRepository;
import pl.example.reactdict.repository.impl.FromFileWordStreamService;
import pl.example.reactdict.repository.impl.WordStreamDictionaryRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WordStreamDictionaryRepositoryTest {

    private static DictionaryRepository dictionaryRepository;

    @BeforeAll
    public static void beforeAll() {
        dictionaryRepository = new WordStreamDictionaryRepository(new FromFileWordStreamService("data//slowa.txt"));
    }

    @Test
    public void testFindingWordsByRegex() {
        String regex = "^d.*ariusz.*";
        Flux<String> result = dictionaryRepository.find(regex).log();
        StepVerifier.create(result)
                .expectNextCount(84)
                .verifyComplete();
    }

    @Test
    public void testFindingWordsByRegex2() {
        String regex = "([^a]*a){6}[^a]*";
        List<List<String>> result = dictionaryRepository.find(regex)
                .buffer(10)
                .doOnNext(list -> log.info("{}", list))
                .collectList().block();
        //log.info("Found: {}", result);
    }

    @Test
    public void testFindingByMultipleRegex() {
        String regexA = "([^a]*a){4}[^a]*";
        String regexT = "([^t]*t){4}[^t]*";
        Flux<String> result = dictionaryRepository.find(List.of(regexA, regexT));
        StepVerifier.create(result)
                .expectNext("antytotalitarna")
                .verifyComplete();
    }

    @Test
    public void testByPart() {
        dictionaryRepository.find(".*du.*ar.*")
                .buffer(10)
                .delayElements(Duration.ofMillis(100))
                .doOnNext(list -> log.info("{}", list))
                .blockLast(Duration.of(20, ChronoUnit.SECONDS));
    }

    @Test
    public void testContains() {
        StepVerifier.create(dictionaryRepository.contains("dyżurka"))
                .assertNext(b -> assertThat(b).isTrue())
                .verifyComplete();

        StepVerifier.create(dictionaryRepository.contains("wdragnik"))
                .assertNext(b -> assertThat(b).isFalse())
                .verifyComplete();
    }

    @Test
    public void testBackpressure() throws InterruptedException {

        StepSubscriber<List<String>> stepSubscriber = new StepSubscriber<>();

        dictionaryRepository.find(".*du.*ar.*")
                .buffer(10)
                .subscribe(stepSubscriber);

        // simple, but not correct approach
        while (Objects.nonNull(stepSubscriber.subscription)) {
            stepSubscriber.subscription.request(1);
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }

    private static class StepSubscriber<T> implements Subscriber<T> {
        Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            log.info("Subscription started");
            subscription = s;
        }

        @Override
        public void onNext(T s) {
            log.info("{}", s);
        }

        @Override
        public void onError(Throwable t) {
            log.error("Error", t);
        }

        @Override
        public void onComplete() {
            subscription = null;
            log.info("Koniec");
        }
    }
}
