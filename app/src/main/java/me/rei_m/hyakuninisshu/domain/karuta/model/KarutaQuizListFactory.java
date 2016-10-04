package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.util.SparseIntArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
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
            int[] collectKarutaIndexList = generateRandomArray(karutaList.size(), size);

            for (int i : collectKarutaIndexList) {
                correctKarutaList.add(karutaList.get(i));
            }
        });

        return karutaRepository.asEntityList().map(karutaList -> {

            List<KarutaQuiz> karutaQuizList = new ArrayList<>();

            for (Karuta correctKaruta : correctKarutaList) {

                List<BottomPhrase> bottomPhraseList = new ArrayList<>();

                int correctIndex = correctKaruta.getIdentifier().getValue() - 1;

                for (int targetIndex : generateRandomArray(karutaList.size() - 1, 3)) {
                    if (targetIndex == correctIndex) {
                        bottomPhraseList.add(karutaList.get(karutaList.size() - 1).getBottomPhrase());
                    } else {
                        bottomPhraseList.add(karutaList.get(targetIndex).getBottomPhrase());
                    }
                }

                int[] correctPosition = generateRandomArray(4, 1);

                bottomPhraseList.add(correctPosition[0], correctKaruta.getBottomPhrase());

                KarutaQuiz karutaQuiz = new KarutaQuiz(bottomPhraseList, correctKaruta);
                karutaQuizList.add(karutaQuiz);
            }

            return karutaQuizList;
        });
    }

    private int[] generateRandomArray(int randMax, int size) {

        int[] randArray = new int[size];
        SparseIntArray conversion = new SparseIntArray();

        Random rand = new Random();

        for (int i = 0, upper = randMax; i < size; i++, upper--) {
            int key = rand.nextInt(upper);
            int val = conversion.get(key, key);

            randArray[i] = val;

            int nextLastIndex = upper - 1;

            conversion.put(key, conversion.get(nextLastIndex, nextLastIndex));
        }
        return randArray;
    }
}
