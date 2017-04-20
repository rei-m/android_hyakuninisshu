package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.model.KarutaModel;
import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class QuizAnswerFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableInt karutaNo = new ObservableInt();

    public final ObservableInt kimariji = new ObservableInt(0);

    public final ObservableField<String> creator = new ObservableField<>("");

    public final ObservableField<String> firstPhrase = new ObservableField<>("");

    public final ObservableField<String> secondPhrase = new ObservableField<>("");

    public final ObservableField<String> thirdPhrase = new ObservableField<>("");

    public final ObservableField<String> fourthPhrase = new ObservableField<>("");

    public final ObservableField<String> fifthPhrase = new ObservableField<>("");

    public final ObservableField<String> karutaImageNo = new ObservableField<>("");

    public final ObservableBoolean existNextQuiz = new ObservableBoolean(false);

    private PublishSubject<Unit> onClickNextQuizEventSubject = PublishSubject.create();
    public Observable<Unit> onClickNextQuizEvent = onClickNextQuizEventSubject;

    private PublishSubject<Unit> onClickConfirmResultEventSubject = PublishSubject.create();
    public Observable<Unit> onClickConfirmResultEvent = onClickConfirmResultEventSubject;

    private PublishSubject<Unit> errorEventSubject = PublishSubject.create();
    public Observable<Unit> errorEvent = errorEventSubject;

    private final KarutaModel karutaModel;

    private KarutaIdentifier karutaIdentifier;

    public QuizAnswerFragmentViewModel(@NonNull KarutaModel karutaModel) {
        this.karutaModel = karutaModel;
    }

    public void onCreate(long karutaId,
                         boolean existNextQuiz) {
        karutaIdentifier = new KarutaIdentifier(karutaId);
        this.existNextQuiz.set(existNextQuiz);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaModel.completeGetKarutaEvent.subscribe(karuta -> {
            karutaNo.set((int) karuta.getIdentifier().getValue());
            kimariji.set(karuta.getKimariji());
            creator.set(karuta.getCreator());
            firstPhrase.set(KarutaDisplayUtil.padSpace(karuta.getTopPhrase().getFirst().getKanji(), 5));
            secondPhrase.set(karuta.getTopPhrase().getSecond().getKanji());
            thirdPhrase.set(karuta.getTopPhrase().getThird().getKanji());
            fourthPhrase.set(KarutaDisplayUtil.padSpace(karuta.getBottomPhrase().getFourth().getKanji(), 7));
            fifthPhrase.set(karuta.getBottomPhrase().getFifth().getKanji());
            karutaImageNo.set("karuta_" + karuta.getImageNo());
        }), karutaModel.error.subscribe(v -> {
            errorEventSubject.onNext(Unit.INSTANCE);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        karutaModel.getKaruta(karutaIdentifier);
    }

    public void onClickNextQuiz(View view) {
        onClickNextQuizEventSubject.onNext(Unit.INSTANCE);
    }

    public void onClickConfirmResult(View view) {
        onClickConfirmResultEventSubject.onNext(Unit.INSTANCE);
    }

    @BindingAdapter({"karutaNo", "kimariji"})
    public static void setKarutaNoAndKimariji(@NonNull TextView view,
                                              int karutaNo,
                                              int kimariji) {
        if (kimariji <= 0) {
            return;
        }

        Context context = view.getContext().getApplicationContext();
        String text = KarutaDisplayUtil.convertNumberToString(context, karutaNo) + " / " +
                KarutaDisplayUtil.convertKimarijiToString(context, kimariji);
        view.setText(text);
    }
}
