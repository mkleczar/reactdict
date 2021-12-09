package pl.example.reactdict.reporsitory.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pl.example.reactdict.repository.DictionaryRepository;
import pl.example.reactdict.repository.impl.FromFileWordStreamService;
import pl.example.reactdict.repository.impl.WordStreamDictionaryRepository;
import reactor.core.publisher.Flux;
import java.util.List;

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
}
