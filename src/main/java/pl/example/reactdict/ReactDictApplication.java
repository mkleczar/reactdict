package pl.example.reactdict;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ReactDictApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ReactDictApplication.class)
                .run(args);
    }
}
