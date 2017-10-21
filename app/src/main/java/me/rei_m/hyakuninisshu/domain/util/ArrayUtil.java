package me.rei_m.hyakuninisshu.domain.util;

import android.util.SparseIntArray;

import java.util.Random;

public class ArrayUtil {

    private ArrayUtil() {
    }

    /**
     * ランダムなインデックスの配列を生成する.
     * <p>
     * ex) generateRandomIndexArray(10, 4) では 0 ~ 9 の中からランダムに4個の要素を選んで配列にして返す.
     *
     * @param randomArraySize 生成する配列の要素数.
     * @param size            生成した配列から返却する数
     * @return ランダム順にIndexを格納した配列
     */
    public static int[] generateRandomIndexArray(int randomArraySize, int size) {

        int[] randArray = new int[size];
        SparseIntArray conversion = new SparseIntArray();

        Random rand = new Random();

        for (int i = 0, upper = randomArraySize; i < size; i++, upper--) {
            int key = rand.nextInt(upper);
            int val = conversion.get(key, key);

            randArray[i] = val;

            int nextLastIndex = upper - 1;

            conversion.put(key, conversion.get(nextLastIndex, nextLastIndex));
        }

        return randArray;
    }
}
