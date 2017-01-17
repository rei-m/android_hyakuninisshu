package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.SingleAssociation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.AbstractEntity;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizChoiceSchema;
import me.rei_m.hyakuninisshu.infrastructure.database.KarutaQuizSchema;

public class KarutaQuiz extends AbstractEntity<KarutaQuiz, KarutaQuizIdentifier> {

    public static KarutaQuiz create(@NonNull KarutaQuizSchema karutaQuizSchema,
                                    @NonNull List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList) {
        KarutaQuizIdentifier karutaQuizIdentifier = new KarutaQuizIdentifier(karutaQuizSchema.quizId);
        List<KarutaIdentifier> choiceList = new ArrayList<>();
        for (KarutaQuizChoiceSchema karutaQuizChoiceSchema : karutaQuizChoiceSchemaList) {
            choiceList.add(new KarutaIdentifier(karutaQuizChoiceSchema.karutaId));
        }
        KarutaIdentifier collectKarutaIdentifier = new KarutaIdentifier(karutaQuizSchema.collectId);
        KarutaQuiz karutaQuiz = new KarutaQuiz(karutaQuizIdentifier,
                choiceList,
                collectKarutaIdentifier);
        karutaQuiz.startDate = karutaQuizSchema.startDate;
        if (0 < karutaQuizSchema.answerTime) {
            karutaQuiz.result = new KarutaQuizResult(collectKarutaIdentifier,
                    karutaQuizSchema.isCollect,
                    karutaQuizSchema.answerTime);
        }
        return karutaQuiz;
    }

    private final KarutaQuizContents contents;

    private Date startDate;

    private KarutaQuizResult result;

    public KarutaQuiz(@NonNull KarutaQuizIdentifier identifier,
                      @NonNull List<KarutaIdentifier> choiceList,
                      @NonNull KarutaIdentifier collectId) {
        super(identifier);
        contents = new KarutaQuizContents(choiceList, collectId);
    }

    public KarutaQuizResult getResult() {
        return result;
    }

    public KarutaQuizContents start(@NonNull Date startDate) {
        this.startDate = startDate;
        return contents;
    }

    public void verify(int choiceNo, @NonNull Date answerDate) {
        // TODO: startをチェックしてnullならエラー
        // TODO: startよりanswerが小さかったらエラー
        // TODO: choiceNoがリストの数より大きかったらエラー
        KarutaIdentifier selectedId = contents.choiceList.get(choiceNo - 1);
        boolean isCollect = contents.collectId.equals(selectedId);
        long answerTime = answerDate.getTime() - startDate.getTime();
        this.result = new KarutaQuizResult(contents.collectId, isCollect, answerTime);
    }

    // TODO: Domain層の作りを見直す。EntityがOrmaObjectを持つ形にする.
    public KarutaQuizSchema toSchema() {

        KarutaQuizSchema karutaQuizSchema = new KarutaQuizSchema();
        karutaQuizSchema.quizId = getIdentifier().getValue();
        karutaQuizSchema.collectId = contents.collectId.getValue();
        karutaQuizSchema.startDate = startDate;
        if (result != null) {
            karutaQuizSchema.answerTime = result.answerTime;
            karutaQuizSchema.isCollect = result.isCollect;
        } else {
            karutaQuizSchema.answerTime = 0;
            karutaQuizSchema.isCollect = false;
        }
        return karutaQuizSchema;
    }

    public List<KarutaQuizChoiceSchema> toSchema(@NonNull KarutaQuizSchema karutaQuizSchema) {
        List<KarutaQuizChoiceSchema> karutaQuizChoiceSchemaList = new ArrayList<>();
        for (KarutaIdentifier karutaIdentifier : contents.choiceList) {
            KarutaQuizChoiceSchema karutaQuizChoiceSchema = new KarutaQuizChoiceSchema();
            karutaQuizChoiceSchema.karutaQuizSchema = SingleAssociation.just(karutaQuizSchema);
            karutaQuizChoiceSchema.karutaId = karutaIdentifier.getValue();
            karutaQuizChoiceSchema.orderNo = contents.choiceList.indexOf(karutaIdentifier);
            karutaQuizChoiceSchemaList.add(karutaQuizChoiceSchema);
        }
        return karutaQuizChoiceSchemaList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KarutaQuiz that = (KarutaQuiz) o;

        return contents.equals(that.contents) &&
                (startDate != null ? startDate.equals(that.startDate) : that.startDate == null
                        && (result != null ? result.equals(that.result) : that.result == null));

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + contents.hashCode();
        result1 = 31 * result1 + (startDate != null ? startDate.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "KarutaQuiz{" +
                "contents=" + contents +
                ", startDate=" + startDate +
                ", result=" + result +
                "} " + super.toString();
    }
}
