package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

public class QuizViewModel {

    public static class QuizChoiceViewModel {

        public final String fourthPhrase;

        public final String fifthPhrase;

        public QuizChoiceViewModel(@NonNull String fourthPhrase,
                                   @NonNull String fifthPhrase) {
            this.fourthPhrase = fourthPhrase;
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

    public final String quizCount;

    public QuizViewModel(@NonNull String quizId,
                         @NonNull String firstPhrase,
                         @NonNull String secondPhrase,
                         @NonNull String thirdPhrase,
                         @NonNull QuizChoiceViewModel choiceFirst,
                         @NonNull QuizChoiceViewModel choiceSecond,
                         @NonNull QuizChoiceViewModel choiceThird,
                         @NonNull QuizChoiceViewModel choiceFourth,
                         String quizCount) {

        this.quizId = quizId;
        this.firstPhrase = firstPhrase;
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;

        this.choiceFirst = choiceFirst;
        this.choiceSecond = choiceSecond;
        this.choiceThird = choiceThird;
        this.choiceFourth = choiceFourth;

        this.quizCount = quizCount;
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
                ", quizCount='" + quizCount + '\'' +
                '}';
    }
}
