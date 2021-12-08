package pl.example.reactdict.repository.impl;

import org.springframework.stereotype.Service;
import pl.example.reactdict.repository.DictionaryRepository;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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
                .filter(w -> pattern.matcher(w).matches()));
    }
}
