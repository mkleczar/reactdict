package pl.example.reactdict.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Service;
import pl.example.reactdict.repository.DictionaryRepository;
import pl.example.reactdict.service.DictionaryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final DictionaryRepository dictionaryRepository;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    @Override
    public Flux<String> allPossibleWords(String allowedLetters) {

        String regexTemplate = "([^%1$s]*%1$s){0,%2$d}[^%1$s]*";
        List<String> regexes = Stream.of(allowedLetters.split("(?!^)"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> String.format(regexTemplate, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        String regexWord = String.format("[%s]{1,%d}", allowedLetters, allowedLetters.length());
        regexes.add(regexWord);

        // log.info("All regexes: {}", regexes);
        return dictionaryRepository.find(regexes);
    }

    @Override
    public Flux<String> allPossibleWords(String allowedLetters, int blanksNumber) {
        // TODO: impl
        return null;
    }

    @Override
    public Flux<String> wordsFromRegex(String regex) {
        return dictionaryRepository.find(regex);
    }

    @Override
    public Mono<Boolean> isWordValid(String word) {
        return dictionaryRepository.contains(word);
    }
}
