package me.rei_m.hyakuninisshu.domain.karuta.model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;

public class KarutaQuizListFactory {

    private final KarutaRepository karutaRepository;

    public KarutaQuizListFactory(KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public Observable<List<KarutaQuiz>> create(int fromKarutaId, int toKarutaId, int kimariji) {

        List<Karuta> correctKarutaList = new ArrayList<>();

        int quizSize = toKarutaId - fromKarutaId + 1;

        Observable<List<Karuta>> karutaListObservable = (kimariji == 0) ?
                karutaRepository.asEntityList(new KarutaIdentifier(fromKarutaId), new KarutaIdentifier(toKarutaId)) :
                karutaRepository.asEntityList(new KarutaIdentifier(fromKarutaId), new KarutaIdentifier(toKarutaId), kimariji);

        karutaListObservable.subscribe(karutaList -> {

            int size = (karutaList.size() < quizSize) ? karutaList.size() : quizSize;
            int[] collectKarutaIndexList = ArrayUtil.generateRandomArray(karutaList.size(), size);

            for (int i : collectKarutaIndexList) {
                correctKarutaList.add(karutaList.get(i));
            }
        });

        return karutaRepository.asEntityList().map(karutaList -> {

            List<KarutaQuiz> karutaQuizList = new ArrayList<>();

            for (Karuta correctKaruta : correctKarutaList) {

                List<KarutaIdentifier> choiceList = new ArrayList<>();

                long correctIndex = correctKaruta.getIdentifier().getValue() - 1;

                for (int targetIndex : ArrayUtil.generateRandomArray(karutaList.size() - 1, 3)) {
                    if (targetIndex == correctIndex) {
                        choiceList.add(karutaList.get(karutaList.size() - 1).getIdentifier());
                    } else {
                        choiceList.add(karutaList.get(targetIndex).getIdentifier());
                    }
                }

                int[] correctPosition = ArrayUtil.generateRandomArray(4, 1);

                choiceList.add(correctPosition[0], correctKaruta.getIdentifier());

                KarutaQuiz karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), choiceList, correctKaruta.getIdentifier());
                karutaQuizList.add(karutaQuiz);
            }

            return karutaQuizList;
        });
    }
}
