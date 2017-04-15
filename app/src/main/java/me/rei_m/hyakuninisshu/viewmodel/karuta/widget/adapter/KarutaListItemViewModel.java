package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.adapter;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.domain.karuta.model.Karuta;
import me.rei_m.hyakuninisshu.presentation.helper.Navigator;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class KarutaListItemViewModel {

    public final ObservableInt karutaNo = new ObservableInt(0);

    public final ObservableField<String> karutaImageNo = new ObservableField<>("");

    public final ObservableField<String> creator = new ObservableField<>("");

    public final ObservableField<String> topPhrase = new ObservableField<>("");

    public final ObservableField<String> bottomPhrase = new ObservableField<>("");

    private final Navigator navigator;

    @Inject
    public KarutaListItemViewModel(@NonNull Navigator navigator) {
        this.navigator = navigator;
    }

    public void setKaruta(@NonNull Karuta karuta) {
        String topPhrase = karuta.getTopPhrase().getFirst().getKanji() + SPACE +
                karuta.getTopPhrase().getSecond().getKanji() + SPACE +
                karuta.getTopPhrase().getThird().getKanji();

        String bottomPhrase = karuta.getBottomPhrase().getFourth().getKanji() + SPACE +
                karuta.getBottomPhrase().getFifth().getKanji();

        int karutaNo = (int) karuta.getIdentifier().getValue();

        this.karutaNo.set(karutaNo);
        this.karutaImageNo.set(karuta.getImageNo());
        this.creator.set(karuta.getCreator());
        this.topPhrase.set(topPhrase);
        this.bottomPhrase.set(bottomPhrase);
    }

    @SuppressWarnings("unused")
    public void onItemClicked(View view) {
        navigator.navigateToMaterialDetail(karutaNo.get());
    }
}
