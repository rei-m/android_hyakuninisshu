package me.rei_m.hyakuninisshu.presentation.utilitty;

import java.util.concurrent.atomic.AtomicInteger;

public class ViewUtil {

    private static final AtomicInteger nextGeneratedId = new AtomicInteger(1);

    private ViewUtil() {
    }

    /**
     * Generate a value suitable for use in [.setId].
     * This value will not collide with ID values generated at build time by aapt for R.id.
     *
     * @return a generated ID value
     */
    public static int generateViewId() {
        while (true) {
            int result = nextGeneratedId.get();
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (nextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }
}