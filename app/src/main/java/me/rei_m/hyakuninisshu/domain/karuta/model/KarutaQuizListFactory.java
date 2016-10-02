package me.rei_m.hyakuninisshu.domain.karuta.model;

import android.util.SparseArray;

import java.util.ArrayList;

import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import rx.Observable;

public class KarutaQuizListFactory {

    private final KarutaRepository karutaRepository;

    public KarutaQuizListFactory(KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    public Observable<KarutaQuiz> create(int size, int kimariji) {

        SparseArray<Karuta> karutaMap = new SparseArray<>();

        karutaRepository.asEntityList().subscribe(karutaList -> {

        });
        
        return Observable.from(new ArrayList<KarutaQuiz>());
    }
}
