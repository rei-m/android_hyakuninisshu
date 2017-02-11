package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum TrainingRangeTo implements SpinnerItem {
    TEN(R.string.training_range_10, "10", 10),
    TWENTY(R.string.training_range_20, "20", 20),
    THIRTY(R.string.training_range_30, "30", 30),
    FORTY(R.string.training_range_40, "40", 40),
    FIFTY(R.string.training_range_50, "50", 50),
    SIXTY(R.string.training_range_60, "60", 60),
    SEVENTY(R.string.training_range_70, "70", 70),
    EIGHTY(R.string.training_range_80, "80", 80),
    NINETY(R.string.training_range_90, "90", 90),
    ONE_HUNDRED(R.string.training_range_100, "100", 100);

    private final int resId;

    private final String code;

    private final int id;

    TrainingRangeTo(int resId, String code, int id) {
        this.resId = resId;
        this.code = code;
        this.id = id;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel(Resources res) {
        return res.getString(resId);
    }

    public int getId() {
        return id;
    }
}
