package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum TrainingRange implements SpinnerItem {
    ALL(R.string.training_range_all, "all", 1, 100),
    ONE_TO_TEN(R.string.training_range_1_10, "1_10", 1, 10),
    ELEVEN_TO_TWENTY(R.string.training_range_11_20, "11_20", 11, 20),
    TWENTY_ONE_TO_THIRTY(R.string.training_range_21_30, "21_30", 21, 30),
    THIRTY_ONE_TO_FORTY(R.string.training_range_31_40, "31_40", 31, 40),
    FORTY_ONE_TO_FIFTY(R.string.training_range_41_50, "41_50", 41, 50),
    FIFTY_ONE_TO_SIXTY(R.string.training_range_51_60, "51_60", 51, 60),
    SIXTY_ONE_TO_SEVENTY(R.string.training_range_61_70, "61_70", 61, 70),
    SEVENTY_ONE_TO_EIGHTY(R.string.training_range_71_80, "71_80", 71, 80),
    EIGHTY_ONE_TO_NINETY(R.string.training_range_81_90, "81_90", 81, 90),
    NINETY_ONE_TO_ONE_HUNDRED(R.string.training_range_91_100, "91_100", 91, 100);

    private final int resId;

    private final String code;

    private final int fromId;

    private final int toId;

    TrainingRange(int resId, String code, int fromId, int toId) {
        this.resId = resId;
        this.code = code;
        this.fromId = fromId;
        this.toId = toId;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel(Resources res) {
        return res.getString(resId);
    }

    public int getFromId() {
        return fromId;
    }

    public int getToId() {
        return toId;
    }
}
