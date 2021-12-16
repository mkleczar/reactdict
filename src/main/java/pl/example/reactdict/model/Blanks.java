package pl.example.reactdict.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Blanks {
    ONE(1),
    TWO(2);

    private final int blanks;

    public static Blanks valueOf(int nr) {
        return Arrays.stream(Blanks.values())
                .filter(b -> b.blanks == nr)
                .findAny()
                .orElse(ONE);
    }
}
