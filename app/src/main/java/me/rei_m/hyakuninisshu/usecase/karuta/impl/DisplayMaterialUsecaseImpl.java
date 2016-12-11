package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialUsecase;

public class DisplayMaterialUsecaseImpl implements DisplayMaterialUsecase {

    private final KarutaRepository karutaRepository;

    public DisplayMaterialUsecaseImpl(@NonNull KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    @Override
    public Single<MaterialViewModel> execute() {
        return karutaRepository.asEntityList().flatMap(karutaList -> Observable.fromIterable(karutaList)
                .map(karuta -> new MaterialViewModel.KarutaViewModel(
                        (int) karuta.getIdentifier().getValue(),
                        karuta.getImageNo(),
                        karuta.getCreator(),
                        karuta.getTopPhrase().getFirst().getKanji(),
                        karuta.getTopPhrase().getSecond().getKanji(),
                        karuta.getTopPhrase().getThird().getKanji(),
                        karuta.getBottomPhrase().getFourth().getKanji(),
                        karuta.getBottomPhrase().getFifth().getKanji()))
                .toList()
                .map(MaterialViewModel::new));
    }
}
