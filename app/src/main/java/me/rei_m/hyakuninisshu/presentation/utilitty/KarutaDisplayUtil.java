package me.rei_m.hyakuninisshu.presentation.utilitty;

public class KarutaDisplayUtil {

    private KarutaDisplayUtil() {
    }

    public static String padSpace(String text, int count) {

        int finallyCount = (count < text.length()) ? text.length() : count;

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < finallyCount; i++) {
            builder.append("　");
        }
        return (text + builder.toString()).substring(0, finallyCount);
    }
}
