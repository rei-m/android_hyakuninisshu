package me.rei_m.hyakuninisshu.domain.karuta.model;

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.domain.util.ArrayUtil;
import rx.Observable;

public class KarutaQuizListFactory {

    private final KarutaRepository karutaRepository;

    public KarutaQuizListFactory(KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public Observable<List<KarutaQuiz>> create(int quizSize, int kimariji) {

        List<Karuta> correctKarutaList = new ArrayList<>();

        karutaRepository.asEntityList().subscribe(karutaList -> {

            int size = (karutaList.size() < quizSize) ? karutaList.size() : quizSize;
            int[] collectKarutaIndexList = ArrayUtil.generateRandomArray(karutaList.size(), size);

            for (int i : collectKarutaIndexList) {
                correctKarutaList.add(karutaList.get(i));
            }
        });

        return karutaRepository.asEntityList().map(karutaList -> {

            List<KarutaQuiz> karutaQuizList = new ArrayList<>();

            for (Karuta correctKaruta : correctKarutaList) {

                List<BottomPhrase> bottomPhraseList = new ArrayList<>();

                long correctIndex = correctKaruta.getIdentifier().getValue() - 1;

                for (int targetIndex : ArrayUtil.generateRandomArray(karutaList.size() - 1, 3)) {
                    if (targetIndex == correctIndex) {
                        bottomPhraseList.add(karutaList.get(karutaList.size() - 1).getBottomPhrase());
                    } else {
                        bottomPhraseList.add(karutaList.get(targetIndex).getBottomPhrase());
                    }
                }

                int[] correctPosition = ArrayUtil.generateRandomArray(4, 1);

                bottomPhraseList.add(correctPosition[0], correctKaruta.getBottomPhrase());

                KarutaQuiz karutaQuiz = new KarutaQuiz(new KarutaQuizIdentifier(), bottomPhraseList, correctKaruta);
                karutaQuizList.add(karutaQuiz);
            }

            return karutaQuizList;
        });
    }
}
