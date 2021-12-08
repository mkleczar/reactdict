package pl.example.reactdict.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
@Service
public class FromFileWordStreamService implements Supplier<Stream<String>> {

    // TODO: get from properties
    private final String fileName = "data\\slowa.txt";

    @Override
    public Stream<String> get() {
        try {
            return Files.lines(Paths.get(new ClassPathResource(fileName).getURI()));
        } catch (IOException e) {
            log.error("Cant read stream of woerds from file: {}", fileName);
            log.error(e.getMessage(), e);
        }
        return Stream.empty();
    }
}
