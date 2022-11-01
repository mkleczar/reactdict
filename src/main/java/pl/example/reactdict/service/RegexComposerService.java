package pl.example.reactdict.service;

import pl.example.reactdict.model.Blanks;

import java.util.List;

public interface RegexComposerService {
    List<String> regexForWordFromLetters(String letters);
    List<String> regexForWordFromLetters(String letters, Blanks blanks);
}
