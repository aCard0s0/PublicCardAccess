package dev.pca.services.perdicates;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class UtilsPredicates {

    public static Pattern parseToCaseSensitiveRegex(String searchString) {
        return Pattern.compile(Pattern.quote(searchString));
    }

    public static Pattern parseToCaseInsensitiveRegex(String searchString) {
        return Pattern.compile(Pattern.quote(searchString), Pattern.CASE_INSENSITIVE);
    }

    public static List<String> getPossibleMultipleParamsFromRequestParams(String values) {
        return Arrays.asList(values.split("\\s*,\\s*"));
    }
}
