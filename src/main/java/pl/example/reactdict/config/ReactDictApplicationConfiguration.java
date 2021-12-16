package pl.example.reactdict.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@Configuration
@Slf4j
public class ReactDictApplicationConfiguration {

    @Bean
    public CommandLineRunner propertiesLogger(Environment environment) {
        return args ->
                ((AbstractEnvironment)environment).getPropertySources().stream()
                        .filter(ps -> ps instanceof EnumerablePropertySource)
                        .map(ps -> ((EnumerablePropertySource<?>)ps))
                        .peek(ps -> log.info("Property source: {}", ps.getName()))
                        .map(EnumerablePropertySource::getPropertyNames)
                        .flatMap(Arrays::stream)
                        .distinct()
                        .sorted()
                        .forEach(p -> log.info("Property: {}={}", p, p.toLowerCase().contains("pass") ? "*****" : environment.getProperty(p)));
    }
}
