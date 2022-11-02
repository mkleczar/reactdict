package pl.example.reactdict.service.impl;

import org.springframework.stereotype.Service;
import pl.example.reactdict.model.Blanks;
import pl.example.reactdict.service.RegexComposerService;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RegexComposerJavaServiceImpl implements RegexComposerService {
    @Override
    public List<String> regexForWordFromLetters(String allowedLetters) {
        List<String> regexes = regexesWithLetterLimits(allowedLetters);
        String regexWord = String.format("^[%s]{1,%d}$", allowedLetters, allowedLetters.length());
        regexes.add(regexWord);
        return regexes;
    }

    @Override
    public List<String> regexForWordFromLetters(String allowedLetters, Blanks blanks) {
        List<String> regexes = regexesWithLetterLimits(allowedLetters);
        String regexWord = String.format("^([%1$s]*.{1}){0,%2$d}[%1$s]*$", allowedLetters, blanks.getBlanks());
        regexes.add(regexWord);
        return regexes;
    }

    private List<String> regexesWithLetterLimits(String allowedLetters) {
        String regexTemplate = "^([^%1$s]*%1$s){0,%2$d}[^%1$s]*$";
        return Stream.of(allowedLetters.split("(?!^)"))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> String.format(regexTemplate, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
