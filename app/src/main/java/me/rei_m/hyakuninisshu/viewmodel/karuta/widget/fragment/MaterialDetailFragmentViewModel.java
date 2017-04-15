package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.widget.TextView;

import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;
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

            if (!karutaIdentifier.equals(karuta.getIdentifier())) {
                return;
            }

            karutaNo.set((int) karutaIdentifier.getValue());

            karutaImageNo.set(karuta.getImageNo());

            creator.set(karuta.getCreator());

            kimariji.set(karuta.getKimariji());

            topPhraseKanji.set(karuta.getTopPhrase().getFirst().getKanji() + SPACE +
                    karuta.getTopPhrase().getSecond().getKanji() + SPACE +
                    karuta.getTopPhrase().getThird().getKanji());


            bottomPhraseKanji.set(karuta.getBottomPhrase().getFourth().getKanji() + SPACE +
                    karuta.getBottomPhrase().getFifth().getKanji());

            topPhraseKana.set(karuta.getTopPhrase().getFirst().getKana() + SPACE +
                    karuta.getTopPhrase().getSecond().getKana() + SPACE +
                    karuta.getTopPhrase().getThird().getKana());

            bottomPhraseKana.set(karuta.getBottomPhrase().getFourth().getKana() + SPACE +
                    karuta.getBottomPhrase().getFifth().getKana());

            translation.set(karuta.getTranslation());
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

        String text = KarutaDisplayUtil.convertNumberToString(context, karutaNo) + " / " + creator;
        view.setText(text);
    }
}
