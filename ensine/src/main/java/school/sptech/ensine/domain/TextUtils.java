package school.sptech.ensine.domain;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TextUtils {
    private static final List<String> STOP_WORDS = Arrays.asList(
            "a", "ao", "aos", "aquela", "aquelas", "aquele", "aqueles", "aquilo", "as", "até", "com", "como", "da", "das",
            "de", "dela", "delas", "dele", "deles", "do", "dos", "e", "ela", "elas", "ele", "eles", "em", "entre", "essa",
            "essas", "esse", "esses", "esta", "estas", "este", "estes", "eu", "isso", "isto", "já", "lhe", "lhes", "mais",
            "mas", "me", "mesmo", "meu", "meus", "minha", "minhas", "muito", "na", "nas", "não", "no", "nos", "nossa",
            "nossas", "nosso", "nossos", "num", "numa", "nós", "o", "os", "ou", "para", "pela", "pelas", "pelo", "pelos",
            "por", "quais", "qual", "quando", "que", "quem", "se", "sem", "seu", "seus", "sua", "suas", "só", "são", "também",
            "te", "tem", "tenho", "ter", "teu", "teus", "teve", "tua", "tuas", "um", "uma", "você", "vocês", "vos", "à",
            "às", "é", "ser", "seja", "sejam", "foi", "fui", "foram"
    );

    public static String tokenizeNormalizeRemoveStopWords(String text) {
        // Remove accents and diacritics from the text
        String normalizedText = Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");

        // Tokenize the text into individual words
        String[] words = normalizedText.toLowerCase().split("\\s+");

        // Remove stop words from the tokenized words
        List<String> tokenizedText = Arrays.stream(words)
                .filter(word -> !STOP_WORDS.contains(word))
                .collect(Collectors.toList());

        // Join the tokenized words into a single string
        String result = String.join(" ", tokenizedText);

        return result;
    }
}

