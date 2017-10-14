package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class MaterialEditFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableField<String> creator = new ObservableField<>();

    public final ObservableInt kimariji = new ObservableInt();

    public final ObservableField<String> firstPhraseKanji = new ObservableField<>();

    public final ObservableField<String> firstPhraseKana = new ObservableField<>();

    public final ObservableField<String> secondPhraseKanji = new ObservableField<>();

    public final ObservableField<String> secondPhraseKana = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKanji = new ObservableField<>();

    public final ObservableField<String> thirdPhraseKana = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fourthPhraseKana = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKanji = new ObservableField<>();

    public final ObservableField<String> fifthPhraseKana = new ObservableField<>();

    private final PublishSubject<Unit> onClickEditEventSubject = PublishSubject.create();
    public final Observable<Unit> onClickEditEvent = onClickEditEventSubject;

    private final PublishSubject<Unit> onErrorEditEventSubject = PublishSubject.create();
    public final Observable<Unit> onErrorEditEvent = onErrorEditEventSubject;

    private final PublishSubject<Unit> onUpdateMaterialEventSubject = PublishSubject.create();
    public final Observable<Unit> onUpdateMaterialEvent = onUpdateMaterialEventSubject;

    private final KarutaModel karutaModel;

    private KarutaIdentifier karutaIdentifier;

    public MaterialEditFragmentViewModel(KarutaModel karutaModel) {
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

            karutaNo.set((int) karuta.identifier().value());
            creator.set(karuta.creator());
            kimariji.set(karuta.kimariji());
            firstPhraseKanji.set(karuta.topPhrase().first().kanji());
            firstPhraseKana.set(karuta.topPhrase().first().kana());
            secondPhraseKanji.set(karuta.topPhrase().second().kanji());
            secondPhraseKana.set(karuta.topPhrase().second().kana());
            thirdPhraseKanji.set(karuta.topPhrase().third().kanji());
            thirdPhraseKana.set(karuta.topPhrase().third().kana());
            fourthPhraseKanji.set(karuta.bottomPhrase().fourth().kanji());
            fourthPhraseKana.set(karuta.bottomPhrase().fourth().kana());
            fifthPhraseKanji.set(karuta.bottomPhrase().fifth().kanji());
            fifthPhraseKana.set(karuta.bottomPhrase().fifth().kana());
        }), karutaModel.completeEditKarutaEvent.subscribe(v -> {
            onUpdateMaterialEventSubject.onNext(Unit.INSTANCE);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        karutaModel.getKaruta(karutaIdentifier);
    }

    @SuppressWarnings("unused")
    public void onClickEdit(View view) {
        if (firstPhraseKanji.get().isEmpty() ||
                firstPhraseKana.get().isEmpty() ||
                secondPhraseKanji.get().isEmpty() ||
                secondPhraseKana.get().isEmpty() ||
                thirdPhraseKanji.get().isEmpty() ||
                thirdPhraseKana.get().isEmpty() ||
                fourthPhraseKanji.get().isEmpty() ||
                fourthPhraseKana.get().isEmpty() ||
                fifthPhraseKanji.get().isEmpty() ||
                firstPhraseKana.get().isEmpty()) {
            onErrorEditEventSubject.onNext(Unit.INSTANCE);
            return;
        }
        onClickEditEventSubject.onNext(Unit.INSTANCE);
    }

    public void onClickDialogPositive() {
        karutaModel.editKaruta(karutaIdentifier,
                firstPhraseKanji.get(),
                firstPhraseKana.get(),
                secondPhraseKanji.get(),
                secondPhraseKana.get(),
                thirdPhraseKanji.get(),
                thirdPhraseKana.get(),
                fourthPhraseKanji.get(),
                fourthPhraseKana.get(),
                fifthPhraseKanji.get(),
                fifthPhraseKana.get());
    }
}
