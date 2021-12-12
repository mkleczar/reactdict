package pl.example.reactdict.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.example.reactdict.service.DictionaryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/test")
    public String test() {
        return "To jest test";
    }

    @GetMapping(value = "/letters/{letters}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> scrabble(@PathVariable String letters) {
        log.info("Request: scrabble for letters '{}'", letters);
        return dictionaryService.allPossibleWords(letters);
    }

    @GetMapping("/isValid/{word}")
    public Mono<Boolean> isValid(@PathVariable String word) {
        log.info("Request: isValid for word: {}", word);
        return dictionaryService.isWordValid(word);
    }
}
