package pl.example.reactdict.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Blanks {
    ONE(1),
    TWO(2);

    private final int blanks;

}
