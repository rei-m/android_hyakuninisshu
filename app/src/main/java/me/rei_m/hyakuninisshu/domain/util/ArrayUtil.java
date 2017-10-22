/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

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
     * @param randomArraySize 生成する配列の要素数
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
