package me.rei_m.hyakuninisshu.presentation.karuta.constant;

import android.content.res.Resources;

import java.io.Serializable;

public interface SpinnerItem extends Serializable {

    String code();

    String label(Resources res);
}
