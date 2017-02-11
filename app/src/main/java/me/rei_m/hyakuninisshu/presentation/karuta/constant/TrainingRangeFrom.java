package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import me.rei_m.hyakuninisshu.R;

public enum TrainingRangeFrom implements SpinnerItem {

    ONE(R.string.training_range_1, "1", 1),
    ELEVEN(R.string.training_range_11, "11", 11),
    TWENTY_ONE(R.string.training_range_21, "21", 21),
    THIRTY_ONE(R.string.training_range_31, "31", 31),
    FORTY_ONE(R.string.training_range_41, "41", 41),
    FIFTY_ONE(R.string.training_range_51, "51", 51),
    SIXTY_ONE(R.string.training_range_61, "61", 61),
    SEVENTY_ONE(R.string.training_range_71, "71", 71),
    EIGHTY_ONE(R.string.training_range_81, "81", 81),
    NINETY_ONE(R.string.training_range_91, "91", 91);

    private final int resId;

    private final String code;

    private final int id;

    TrainingRangeFrom(int resId, String code, int id) {
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
