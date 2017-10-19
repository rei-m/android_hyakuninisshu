package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.widget.TextView;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.helper.KarutaDisplayHelper;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class MaterialDetailFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableField<String> karutaImageNo = new ObservableField<>();

    public final ObservableField<String> creator = new ObservableField<>();

    public final ObservableInt kimariji = new ObservableInt();

    public final ObservableField<String> topPhraseKanji = new ObservableField<>();

    public final ObservableField<String> bottomPhraseKanji = new ObservableField<>();

    public final ObservableField<String> topPhraseKana = new ObservableField<>();

    public final ObservableField<String> bottomPhraseKana = new ObservableField<>();

    public final ObservableField<String> translation = new ObservableField<>();

    private final KarutaModel karutaModel;

    private KarutaIdentifier karutaIdentifier;

    public MaterialDetailFragmentViewModel(KarutaModel karutaModel) {
        this.karutaModel = karutaModel;
    }

    public void onCreate(int karutaNo) {
        karutaIdentifier = new KarutaIdentifier(karutaNo);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaModel.completeGetKarutaEvent.subscribe(karuta -> {

            if (!karutaIdentifier.equals(karuta.identifier())) {
                return;
            }

            karutaNo.set((int) karutaIdentifier.value());

            karutaImageNo.set(karuta.imageNo().value());

            creator.set(karuta.creator());

            kimariji.set(karuta.kimariji().value());

            topPhraseKanji.set(karuta.topPhrase().first().kanji() + SPACE +
                    karuta.topPhrase().second().kanji() + SPACE +
                    karuta.topPhrase().third().kanji());


            bottomPhraseKanji.set(karuta.bottomPhrase().fourth().kanji() + SPACE +
                    karuta.bottomPhrase().fifth().kanji());

            topPhraseKana.set(karuta.topPhrase().first().kana() + SPACE +
                    karuta.topPhrase().second().kana() + SPACE +
                    karuta.topPhrase().third().kana());

            bottomPhraseKana.set(karuta.bottomPhrase().fourth().kana() + SPACE +
                    karuta.bottomPhrase().fifth().kana());

            translation.set(karuta.translation());
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        karutaModel.getKaruta(karutaIdentifier);
    }

    @BindingAdapter({"karutaNo", "creator"})
    public static void setKarutaNoAndCreator(@NonNull TextView view,
                                             int karutaNo,
                                             @NonNull String creator) {

        Context context = view.getContext().getApplicationContext();

        String text = KarutaDisplayHelper.convertNumberToString(context, karutaNo) + " / " + creator;
        view.setText(text);
    }
}
