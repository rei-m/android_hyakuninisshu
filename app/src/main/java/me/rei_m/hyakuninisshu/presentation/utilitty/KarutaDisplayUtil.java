package me.rei_m.hyakuninisshu.presentation.utilitty;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.R;

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

    public static String convertNumberToString(@NonNull Context context, int number) {

        if (number == 100) {
            return context.getString(R.string.hundred);
        }

        Resources resources = context.getResources();
        String[] numArray = resources.getStringArray(R.array.number);

        int doubleDigits = number / 10;
        int singleDigits = number % 10;

        StringBuilder sb = new StringBuilder();
        if (0 < doubleDigits) {
            if (1 < doubleDigits) {
                sb.append(numArray[doubleDigits - 1]);
            }
            sb.append(resources.getString(R.string.ten));
        }
        
        if (0 < singleDigits) {
            sb.append(numArray[singleDigits - 1]);
        }

        return context.getString(R.string.karuta_number, sb.toString());
    }

    public static String convertKimarijiToString(@NonNull Context context, int kimariji) {
        Resources resources = context.getResources();
        String[] kimarijiArray = resources.getStringArray(R.array.kimariji);
        return kimarijiArray[kimariji - 1];
    }
}
