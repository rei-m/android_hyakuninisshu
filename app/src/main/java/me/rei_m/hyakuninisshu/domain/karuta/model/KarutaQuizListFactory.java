package me.rei_m.hyakuninisshu.domain.karuta.model;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
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

                long correctIndex = correctKarutaId.getValue() - 1;

                for (int targetIndex : ArrayUtil.generateRandomArray(karutaList.size() - 1, 3)) {
                    if (targetIndex == correctIndex) {
                        choiceList.add(karutaList.get(karutaList.size() - 1).getIdentifier());
                    } else {
                        choiceList.add(karutaList.get(targetIndex).getIdentifier());
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
