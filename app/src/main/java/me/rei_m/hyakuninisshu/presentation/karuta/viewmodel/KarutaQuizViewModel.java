package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class KarutaQuizViewModel {

    public static class KarutaQuizChoiceViewModel {
        public final long id;

        public final String fourthPhrase;

        public final String fifthPhrase;

        public KarutaQuizChoiceViewModel(long id,
                                         @NonNull String fourthPhrase,
                                         @NonNull String fifthPhrase) {
            this.id = id;
            this.fourthPhrase = fourthPhrase;
            this.fifthPhrase = fifthPhrase;
        }
    }

    public final String firstPhrase;

    public final String secondPhrase;

    public final String thirdPhrase;

    public final KarutaQuizChoiceViewModel choiceFirst;

    public final KarutaQuizChoiceViewModel choiceSecond;

    public final KarutaQuizChoiceViewModel choiceThird;

    public final KarutaQuizChoiceViewModel choiceFourth;

    public KarutaQuizViewModel(String firstPhrase,
                               String secondPhrase,
                               String thirdPhrase,
                               KarutaQuizChoiceViewModel choiceFirst,
                               KarutaQuizChoiceViewModel choiceSecond,
                               KarutaQuizChoiceViewModel choiceThird,
                               KarutaQuizChoiceViewModel choiceFourth) {
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;
        this.choiceFirst = choiceFirst;
        this.choiceSecond = choiceSecond;
        this.choiceThird = choiceThird;
        this.choiceFourth = choiceFourth;
    }
}
