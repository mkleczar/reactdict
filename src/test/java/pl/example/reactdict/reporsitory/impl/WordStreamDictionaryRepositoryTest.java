package pl.example.reactdict.reporsitory.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import pl.example.reactdict.repository.DictionaryRepository;
import pl.example.reactdict.repository.impl.FromFileWordStreamService;
import pl.example.reactdict.repository.impl.WordStreamDictionaryRepository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class WordStreamDictionaryRepositoryTest {

    @Test
    public void testFindingWordsByRegex() {
        DictionaryRepository repo = new WordStreamDictionaryRepository(new FromFileWordStreamService("data//slowa.txt"));
        Flux<String> result = repo.find("d.*ariusz.*");
        List<String> list = result.collectList().block();
        assertThat(list)
                .hasSize(84)
                .contains("diariusz", "depozytariusz");
    }

    @Test
    public void testByPart() {
        DictionaryRepository repo = new WordStreamDictionaryRepository(new FromFileWordStreamService("data//slowa.txt"));
        repo.find(".*du.*ar.*")
                .buffer(10)
                .delayElements(Duration.ofMillis(100))
                .doOnNext(list -> log.info("{}", list))
                .blockLast(Duration.of(20, ChronoUnit.SECONDS));
    }

    @Test
    public void testBackpressure() throws InterruptedException {

        StepSubscriber<List<String>> stepSubscriber = new StepSubscriber<>();

        DictionaryRepository repo = new WordStreamDictionaryRepository(new FromFileWordStreamService("data//slowa.txt"));
        repo.find(".*du.*ar.*")
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
