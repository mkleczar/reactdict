package pl.example.reactdict.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.example.reactdict.service.DictionaryService;

@Slf4j
@SpringBootTest
public class DictionaryServiceIntegrationTest {

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    public void test() {
        String str = "mmaa";
        dictionaryService.allPossibleWords(str)
                .doOnNext(w -> log.info("{}", w))
                .collectList()
                .block();
    }
}
