package me.rei_m.hyakuninisshu.presentation.karuta.viewmodel;

import android.support.annotation.NonNull;

import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;

public class QuizAnswerViewModel {

    public final String karutaNo;

    public final String creator;

    public final String firstPhrase;

    public final String secondPhrase;

    public final String thirdPhrase;

    public final String fourthPhrase;

    public final String fifthPhrase;

    public final String karutaImageNo;

    public final boolean isCollect;

    public final boolean existNextQuiz;

    public QuizAnswerViewModel(@NonNull String karutaNo,
                               @NonNull String creator,
                               @NonNull String firstPhrase,
                               @NonNull String secondPhrase,
                               @NonNull String thirdPhrase,
                               @NonNull String fourthPhrase,
                               @NonNull String fifthPhrase,
                               @NonNull String karutaImageNo,
                               boolean isCollect,
                               boolean existNextQuiz) {
        this.karutaNo = karutaNo;
        this.creator = creator;
        this.firstPhrase = KarutaDisplayUtil.padSpace(firstPhrase, 5);
        this.secondPhrase = secondPhrase;
        this.thirdPhrase = thirdPhrase;
        this.fourthPhrase = KarutaDisplayUtil.padSpace(fourthPhrase, 7);
        this.fifthPhrase = fifthPhrase;
        this.karutaImageNo = karutaImageNo;
        this.isCollect = isCollect;
        this.existNextQuiz = existNextQuiz;
    }

    @Override
    public String toString() {
        return "QuizAnswerViewModel{" +
                "karutaNo='" + karutaNo + '\'' +
                ", creator='" + creator + '\'' +
                ", firstPhrase='" + firstPhrase + '\'' +
                ", secondPhrase='" + secondPhrase + '\'' +
                ", thirdPhrase='" + thirdPhrase + '\'' +
                ", fourthPhrase='" + fourthPhrase + '\'' +
                ", fifthPhrase='" + fifthPhrase + '\'' +
                ", isCollect=" + isCollect +
                ", existNextQuiz=" + existNextQuiz +
                '}';
    }
}
