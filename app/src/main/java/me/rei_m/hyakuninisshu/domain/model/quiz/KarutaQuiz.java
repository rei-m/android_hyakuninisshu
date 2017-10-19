package me.rei_m.hyakuninisshu.domain.model.quiz;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

public class KarutaQuiz extends AbstractEntity<KarutaQuiz, KarutaQuizIdentifier> {

    private final List<KarutaIdentifier> choiceList;

    private final KarutaIdentifier collectId;

    private Date startDate;

    private KarutaQuizResult result;

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier collectId) {
        super(identifier);
        this.choiceList = choiceList;
        this.collectId = collectId;
    }

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier collectId,
                      @Nullable Date startDate,
                      long answerTime,
                      int choiceNo,
                      boolean isCollect) {
        this(identifier, choiceList, collectId);
        this.startDate = startDate;
        if (0 < answerTime) {
            this.result = new KarutaQuizResult(collectId,
                    choiceNo,
                    isCollect,
                    answerTime);
        }
    }

    public List<KarutaIdentifier> choiceList() {
        return Collections.unmodifiableList(choiceList);
    }

    public KarutaIdentifier collectId() {
        return collectId;
    }

    public Date startDate() {
        return startDate;
    }

    public KarutaQuizResult result() {
        return result;
    }

    public KarutaQuiz start(@NonNull Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public KarutaQuiz verify(int choiceNo, @NonNull Date answerDate) throws IllegalStateException, IllegalArgumentException {
        if (choiceList.size() < choiceNo) {
            throw new IllegalArgumentException("Invalid choiceNo. " + choiceNo);
        }
        if (startDate == null) {
            throw new IllegalStateException("Quiz is not started. Call start.");
        }
        KarutaIdentifier selectedId = choiceList.get(choiceNo - 1);
        boolean isCollect = collectId.equals(selectedId);
        long answerTime = answerDate.getTime() - startDate.getTime();
        this.result = new KarutaQuizResult(collectId, choiceNo, isCollect, answerTime);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KarutaQuiz that = (KarutaQuiz) o;

        if (!choiceList.equals(that.choiceList)) return false;
        if (!collectId.equals(that.collectId)) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null)
            return false;
        return result != null ? result.equals(that.result) : that.result == null;

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + choiceList.hashCode();
        result1 = 31 * result1 + collectId.hashCode();
        result1 = 31 * result1 + (startDate != null ? startDate.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "KarutaQuiz{" +
                "choiceList=" + choiceList +
                ", collectId=" + collectId +
                ", startDate=" + startDate +
                ", result=" + result +
                "} " + super.toString();
    }
}
