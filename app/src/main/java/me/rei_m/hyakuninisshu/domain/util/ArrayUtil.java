package me.rei_m.hyakuninisshu.domain.util;

import android.util.SparseIntArray;

import java.util.Random;

public class ArrayUtil {

    private ArrayUtil() {
    }

    public static int[] generateRandomArray(int randMax, int size) {

        int[] randArray = new int[size];
        SparseIntArray conversion = new SparseIntArray();

        Random rand = new Random();

        for (int i = 0, upper = randMax; i < size; i++, upper--) {
            int key = rand.nextInt(upper);
            int val = conversion.get(key, key);

            randArray[i] = val;

            int nextLastIndex = upper - 1;

            conversion.put(key, conversion.get(nextLastIndex, nextLastIndex));
        }
        return randArray;
    }
}
