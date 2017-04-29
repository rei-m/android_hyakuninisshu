package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
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

            if (!karutaIdentifier.equals(karuta.getIdentifier())) {
                return;
            }

            karutaNo.set((int) karuta.getIdentifier().getValue());
            creator.set(karuta.getCreator());
            kimariji.set(karuta.getKimariji());
            firstPhraseKanji.set(karuta.getTopPhrase().getFirst().getKanji());
            firstPhraseKana.set(karuta.getTopPhrase().getFirst().getKana());
            secondPhraseKanji.set(karuta.getTopPhrase().getSecond().getKanji());
            secondPhraseKana.set(karuta.getTopPhrase().getSecond().getKana());
            thirdPhraseKanji.set(karuta.getTopPhrase().getThird().getKanji());
            thirdPhraseKana.set(karuta.getTopPhrase().getThird().getKana());
            fourthPhraseKanji.set(karuta.getBottomPhrase().getFourth().getKanji());
            fourthPhraseKana.set(karuta.getBottomPhrase().getFourth().getKana());
            fifthPhraseKanji.set(karuta.getBottomPhrase().getFifth().getKanji());
            fifthPhraseKana.set(karuta.getBottomPhrase().getFifth().getKana());
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
