package pl.example.reactdict.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.example.reactdict.model.Blanks;
import pl.example.reactdict.service.DictionaryService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/test")
    public Mono<String> test() {
        return Mono.just("{\"value\":\"To jest test\"}");
    }

    @GetMapping(value = "/letters/{letters}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> scrabble(@PathVariable String letters) {
        log.info("Request: scrabble for letters '{}'", letters);
        return dictionaryService.allPossibleWords(letters);
    }

    @GetMapping(value = "/letters/{letters}/blanks/{nr}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> scrabble(@PathVariable String letters, @PathVariable int nr) {
        log.info("Request: scrabble for letters '{}' with {} blanks", letters, nr);
        return dictionaryService.allPossibleWords(letters, Blanks.valueOf(nr));
    }

    @GetMapping("/isValid/{word}")
    public Mono<Boolean> isValid(@PathVariable String word) {
        log.info("Request: isValid for word: {}", word);
        return dictionaryService.isWordValid(word);
    }
}
