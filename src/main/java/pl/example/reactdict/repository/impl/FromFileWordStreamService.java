package pl.example.reactdict.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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


    private final String fileName;

    public FromFileWordStreamService(@Value("${dictionary.file}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public Stream<String> get() {
        try {
            return Files.lines(Paths.get(new ClassPathResource(fileName).getURI()));
        } catch (IOException e) {
            log.error("Cant read stream of words from file: {}", fileName);
            log.error(e.getMessage(), e);
        }
        return Stream.empty();
    }
}
