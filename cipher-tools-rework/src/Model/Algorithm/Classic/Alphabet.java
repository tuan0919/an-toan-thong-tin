package Model.Algorithm.Classic;

import java.util.Map;

public class Alphabet {

    private static final Map<String, String> ALPHABETS = Map.of(
        "EN", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        "VN", "AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXY" +
              "aăâbcdđeêghiklmnoôơpqrstuưvxy" +
              "ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂẰẮẲẴẶÂẦẤẨẪẬ" +
              "ĐÈÉÊỀẾỂỄỆÌÍÒÓÔÕỜỚỞỠỢÙÚƯỪỨỬỮỰ" +
              "àáâãèéêìíòóôõùúăằắẳẵặâầấẩẫậ" +
              "đèéêềếểễệìíòóôõờớởỡợùúưừứửữự" +
              "ÝỲỶỸỴýỳỷỹỵ" +
              "ẲẴẶẰẮẲỎÕỌỒỐỔỖỘỜỚỞỠỢỪỨỬỮỰ"
    );

    private final String alphabet;

    public Alphabet(String language) {
        if (!ALPHABETS.containsKey(language)) {
            throw new IllegalArgumentException("Unsupported language: " + language);
        }
        this.alphabet = ALPHABETS.get(language);
    }

    public String getAlphabet() {
        return alphabet;
    }

    public static final Alphabet EN = new Alphabet("EN");
    public static final Alphabet VN = new Alphabet("VN");
}
