package pl.example.reactdict.repository.mongo.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;

@Slf4j
@SpringBootTest
@EnabledIfEnvironmentVariable(named = "repository.type", matches = "mongodb")
public class DictionaryTest {

    @Autowired
    WordsRepository repository;

    @Test
    public void mongoRepositoryCountTest() {
        Long count = repository.count().block();
        log.info("Liczba rekordów: {}", count);
    }

    @Test
    public void mongoTest() throws InterruptedException {
        repository.findAll().subscribe(w -> log.info("{}", w));
        Thread.sleep(2000);
    }

    @Test
    public void mongoRegex() {
        Long count = repository.findAllByWordMatchesRegex("^skór").count().block();
        log.info("Liczba rekordów: {}", count);
    }
}
