package pl.example.reactdict.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import pl.example.reactdict.repository.DictionaryRepository;
import pl.example.reactdict.repository.mongo.model.Words;
import pl.example.reactdict.repository.mongo.repository.WordsRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConditionalOnProperty(name = "repository.type", havingValue = "mongodb")
public class MongoDictionaryRepository implements DictionaryRepository {

    private final WordsRepository repository;

    public MongoDictionaryRepository(WordsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<String> find(String regex) {
        return repository.findAllByWordMatchesRegex(regex)
                .map(Words::getWord);
    }

    @Override
    public Flux<String> find(List<String> regexes) {
        String totalRegex = regexes.stream()
                .collect(Collectors.joining(")(?=", "(?=", ").*"));
        return repository.findAllByWordMatchesRegex(totalRegex)
                .map(Words::getWord);
    }

    @Override
    public Mono<Boolean> contains(String word) {
        return repository.findByWord(word).hasElement();
    }
}
