package Model.Algorithm.Classic;

import java.util.Arrays;

public enum Alphabet {
    EN("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    VN("AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXY");

    private final String alphabet;

    Alphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getAlphabet() {
        return alphabet;
    }

    public static String[] getAllAlphabets() {
        return Arrays.stream(values())
                .map(Alphabet::getAlphabet)
                .toArray(String[]::new);
    }

    public static String[] getAllNameAlphabets() {
        return Arrays.stream(values())
                .map(Enum::name)
                .toArray(String[]::new);
    }
}
