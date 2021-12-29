package pl.example.reactdict;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ReactDictApplication {

    // to jest test polskiego słownika
    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactDictApplication.class)
                .run(args);
    }
}
