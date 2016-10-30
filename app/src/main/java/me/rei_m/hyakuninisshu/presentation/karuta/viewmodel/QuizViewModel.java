package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;

public class QuizViewModel {

    public static class QuizChoiceViewModel {

        public final String fourthPhrase;

        public final String fifthPhrase;

        public QuizChoiceViewModel(@NonNull String fourthPhrase,
                                   @NonNull String fifthPhrase) {
            this.fourthPhrase = KarutaDisplayUtil.padSpace(fourthPhrase, 7);
            this.fifthPhrase = fifthPhrase;
        }
    }

    public final String quizId;

    public final String firstPhrase;

    public final String secondPhrase;

    public final String thirdPhrase;

    public final QuizChoiceViewModel choiceFirst;

    public final QuizChoiceViewModel choiceSecond;

    public final QuizChoiceViewModel choiceThird;

    public final QuizChoiceViewModel choiceFourth;

    public QuizViewModel(String quizId,
                         String firstPhrase,
                         String secondPhrase,
                         String thirdPhrase,
                         QuizChoiceViewModel choiceFirst,
                         QuizChoiceViewModel choiceSecond,
                         QuizChoiceViewModel choiceThird,
                         QuizChoiceViewModel choiceFourth) {

        this.quizId = quizId;
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;

        this.choiceFirst = choiceFirst;
        this.choiceSecond = choiceSecond;
        this.choiceThird = choiceThird;
        this.choiceFourth = choiceFourth;
    }

    @Override
    public String toString() {
        return "QuizViewModel{" +
                "quizId='" + quizId + '\'' +
                ", firstPhrase='" + firstPhrase + '\'' +
                ", secondPhrase='" + secondPhrase + '\'' +
                ", thirdPhrase='" + thirdPhrase + '\'' +
                ", choiceFirst=" + choiceFirst +
                ", choiceSecond=" + choiceSecond +
                ", choiceThird=" + choiceThird +
                ", choiceFourth=" + choiceFourth +
                '}';
    }
}
