package pl.example.reactdict.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.example.reactdict.model.Blanks;
import pl.example.reactdict.repository.DictionaryRepository;
import pl.example.reactdict.service.DictionaryService;
import pl.example.reactdict.service.RegexComposerService;
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
    private final RegexComposerService regexComposerService;

    public DictionaryServiceImpl(DictionaryRepository dictionaryRepository, RegexComposerService regexComposerService) {
        this.dictionaryRepository = dictionaryRepository;
        this.regexComposerService = regexComposerService;
    }

    @Override
    public Flux<String> allPossibleWords(String allowedLetters) {

        List<String> regexes = regexComposerService.regexForWordFromLetters(allowedLetters);
        return dictionaryRepository.find(regexes)
                .doOnNext(str -> log.info("Wynik dla liter: {}: {}", allowedLetters, str))
                .doOnComplete(() -> log.info("Wyszukiwanie dla liter '{}' zakonczone", allowedLetters))
                ;
    }

    @Override
    public Flux<String> allPossibleWords(String allowedLetters, Blanks blanks) {
        List<String> regexes = regexComposerService.regexForWordFromLetters(allowedLetters, blanks);
        return dictionaryRepository.find(regexes);
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
