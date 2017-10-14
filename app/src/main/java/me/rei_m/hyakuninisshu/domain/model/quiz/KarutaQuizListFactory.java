package me.rei_m.hyakuninisshu.domain.model.quiz;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;

@Singleton
public class KarutaQuizListFactory {

    private final KarutaRepository karutaRepository;

    @Inject
    public KarutaQuizListFactory(KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public Single<List<KarutaQuiz>> create(List<KarutaIdentifier> correctKarutaIdList) {

        return karutaRepository.asEntityList().map(karutaList -> {

            List<KarutaQuiz> karutaQuizList = new ArrayList<>();

            for (KarutaIdentifier correctKarutaId : correctKarutaIdList) {

                List<KarutaIdentifier> choiceList = new ArrayList<>();

                long correctIndex = correctKarutaId.value() - 1;

                for (int targetIndex : ArrayUtil.generateRandomArray(karutaList.size() - 1, 3)) {
                    if (targetIndex == correctIndex) {
                        choiceList.add(karutaList.get(karutaList.size() - 1).identifier());
                    } else {
                        choiceList.add(karutaList.get(targetIndex).identifier());
                    }
                }

                int[] correctPosition = ArrayUtil.generateRandomArray(4, 1);

                choiceList.add(correctPosition[0], correctKarutaId);

                KarutaQuiz karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), choiceList, correctKarutaId);
                karutaQuizList.add(karutaQuiz);
            }

            return karutaQuizList;
        });
    }
}
