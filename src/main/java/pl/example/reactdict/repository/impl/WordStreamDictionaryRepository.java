package pl.example.reactdict.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.example.reactdict.repository.DictionaryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
@Service
public class WordStreamDictionaryRepository implements DictionaryRepository {

    private final Supplier<Stream<String>> words;

    public WordStreamDictionaryRepository(Supplier<Stream<String>> wordListSupplier) {
        this.words = wordListSupplier;
    }

    @Override
    public Flux<String> find(String regex) {
        Pattern pattern = Pattern.compile(regex);
        return Flux.fromStream(words.get()
                .filter(w -> pattern.matcher(w).matches())
                //.peek(s -> log.info("Word: {} match to regex: {}", s, regex))
        );
    }

    @Override
    public Flux<String> find(List<String> regexes) {
        Predicate<String> predicate = regexes.stream()
                .map(Pattern::compile)
                .<Predicate<String>>map(pattern -> (String str) -> pattern.matcher(str).matches())
                .reduce(str -> true, Predicate::and);
        return Flux.fromStream(words.get()
                .filter(predicate)
                //.peek(s -> log.info("Word: {} match to regex list: {}", s, regex))
        );
    }

    @Override
    public Mono<Boolean> contains(String word) {
        return Mono.just(words.get().anyMatch(w -> w.equals(word)));
    }
}
