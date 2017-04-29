package me.rei_m.hyakuninisshu.viewmodel.karuta.widget.fragment;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaQuizIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.model.ToriFuda;
import me.rei_m.hyakuninisshu.model.KarutaQuizModel;
import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaStyle;
import me.rei_m.hyakuninisshu.util.Unit;
import me.rei_m.hyakuninisshu.viewmodel.AbsFragmentViewModel;

public class QuizFragmentViewModel extends AbsFragmentViewModel {

    public final ObservableField<String> quizCount = new ObservableField<>("");

    public final ObservableField<String> firstPhrase = new ObservableField<>("");

    public final ObservableField<String> secondPhrase = new ObservableField<>("");

    public final ObservableField<String> thirdPhrase = new ObservableField<>("");

    public final ObservableList<String> choiceFourthPhraseList = new ObservableArrayList<>();

    public final ObservableList<String> choiceFifthPhraseList = new ObservableArrayList<>();

    public final ObservableList<Boolean> isVisibleChoiceList = new ObservableArrayList<>();

    public final ObservableBoolean isVisibleResult = new ObservableBoolean(false);

    public final ObservableBoolean isCollect = new ObservableBoolean(false);

    private PublishSubject<Unit> startDisplayAnimationEventSubject = PublishSubject.create();
    public Observable<Unit> startDisplayAnimationEvent = startDisplayAnimationEventSubject;

    private PublishSubject<Unit> stopDisplayAnimationEventSubject = PublishSubject.create();
    public Observable<Unit> stopDisplayAnimationEvent = stopDisplayAnimationEventSubject;

    private PublishSubject<Unit> onClickResultEventSubject = PublishSubject.create();
    public Observable<Unit> onClickResultEvent = onClickResultEventSubject;

    private PublishSubject<Unit> errorEventSubject = PublishSubject.create();
    public Observable<Unit> errorEvent = errorEventSubject;

    private final KarutaQuizModel karutaQuizModel;

    private KarutaStyle topPhraseStyle;

    private KarutaStyle bottomPhraseStyle;

    private KarutaQuizIdentifier karutaQuizIdentifier;

    private KarutaIdentifier collectKarutaIdentifier;

    private boolean existNextQuiz = false;

    public QuizFragmentViewModel(@NonNull KarutaQuizModel karutaQuizModel) {
        this.karutaQuizModel = karutaQuizModel;
        choiceFourthPhraseList.addAll(Arrays.asList("", "", "", ""));
        choiceFifthPhraseList.addAll(Arrays.asList("", "", "", ""));
        isVisibleChoiceList.addAll(Arrays.asList(true, true, true, true));
    }

    public String getKarutaQuizId() {
        return karutaQuizIdentifier.getValue();
    }

    public long getCollectKarutaId() {
        return collectKarutaIdentifier.getValue();
    }

    public boolean existNextQuiz() {
        return existNextQuiz;
    }

    public void onCreate(@NonNull KarutaStyle topPhraseStyle,
                         @NonNull KarutaStyle bottomPhraseStyle) {
        this.topPhraseStyle = topPhraseStyle;
        this.bottomPhraseStyle = bottomPhraseStyle;
    }

    public void onReCreate(@NonNull String quizId,
                           @NonNull KarutaStyle topPhraseStyle,
                           @NonNull KarutaStyle bottomPhraseStyle) {
        this.karutaQuizIdentifier = new KarutaQuizIdentifier(quizId);
        this.topPhraseStyle = topPhraseStyle;
        this.bottomPhraseStyle = bottomPhraseStyle;
    }

    @Override
    public void onStart() {
        super.onStart();
        registerDisposable(karutaQuizModel.completeStartEvent.subscribe(isAnswered -> {

            karutaQuizIdentifier = karutaQuizModel.getQuizId();

            quizCount.set(karutaQuizModel.getCurrentPosition());

            firstPhrase.set(karutaQuizModel.getYomiFuda().getFirstPhrase());
            secondPhrase.set(karutaQuizModel.getYomiFuda().getSecondPhrase());
            thirdPhrase.set(karutaQuizModel.getYomiFuda().getThirdPhrase());

            for (int i = 0; i < karutaQuizModel.getToriFudaList().size(); i++) {
                ToriFuda toriFuda = karutaQuizModel.getToriFudaList().get(i);
                choiceFourthPhraseList.set(i, toriFuda.getFourthPhrase());
                choiceFifthPhraseList.set(i, toriFuda.getFifthPhrase());
            }

            if (!isAnswered) {
                startDisplayAnimationEventSubject.onNext(Unit.INSTANCE);
            }
        }), karutaQuizModel.completeAnswerEvent.subscribe(result -> {

            stopDisplayAnimationEventSubject.onNext(Unit.INSTANCE);

            List<Boolean> isVisibleChoiceList = Arrays.asList(false, false, false, false);
            isVisibleChoiceList.set(result.choiceNo - 1, true);
            for (int i = 0; i < isVisibleChoiceList.size(); i++) {
                this.isVisibleChoiceList.set(i, isVisibleChoiceList.get(i));
            }
            this.isCollect.set(result.isCollect);
            this.isVisibleResult.set(true);

            this.collectKarutaIdentifier = result.collectKarutaId;
            this.existNextQuiz = karutaQuizModel.existNextQuiz();

        }), karutaQuizModel.error.subscribe(v -> {
            errorEventSubject.onNext(Unit.INSTANCE);
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (karutaQuizIdentifier == null) {
            karutaQuizModel.start(topPhraseStyle, bottomPhraseStyle);
        } else {
            karutaQuizModel.restart(karutaQuizIdentifier, topPhraseStyle, bottomPhraseStyle);
        }
    }

    public void onClickChoice(int choiceNo) {
        karutaQuizModel.answer(choiceNo);
    }

    public void onClickResult() {
        onClickResultEventSubject.onNext(Unit.INSTANCE);
    }

    @BindingAdapter({"textForQuiz", "textPosition"})
    public static void setTextForQuiz(@NonNull TextView view,
                                      @NonNull String text,
                                      int textPosition) {
        if (text.length() < textPosition) {
            return;
        }
        view.setText(text.substring(textPosition - 1, textPosition));
    }

    @BindingAdapter({"isCollect"})
    public static void setIsCollect(@NonNull ImageView imageView,
                                    boolean isCollect) {
        if (isCollect) {
            Glide.with(imageView.getContext()).load(R.drawable.check_correct).dontAnimate().into(imageView);
        } else {
            Glide.with(imageView.getContext()).load(R.drawable.check_incorrect).dontAnimate().into(imageView);
        }
    }
}
