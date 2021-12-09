package pl.example.reactdict.repository.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class FromFileWordStreamService implements Supplier<Stream<String>> {

    @Value("${dictionary.file}")
    private String fileName;

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
