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

//    private QuizState quizState;

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
//        this.quizState = QuizState.UNANSWERED;
    }

//    @Bindable
//    public QuizState getQuizState() {
//        return quizState;
//    }

//    public void setQuizState(QuizState quizState) {
//        this.quizState = quizState;
//        notifyPropertyChanged(BR.quizState);
//    }
}
