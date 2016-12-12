package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.support.annotation.NonNull;

import io.reactivex.Maybe;
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialDetailViewModel;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialDetailUsecase;

public class DisplayMaterialDetailUsecaseImpl implements DisplayMaterialDetailUsecase {

    private final KarutaRepository karutaRepository;

    public DisplayMaterialDetailUsecaseImpl(@NonNull KarutaRepository karutaRepository) {
        this.karutaRepository = karutaRepository;
    }

    @Override
    public Maybe<MaterialDetailViewModel> execute(int karutaNo) {
        return karutaRepository.resolve(new KarutaIdentifier(karutaNo))
                .map(karuta -> new MaterialDetailViewModel(karuta.getImageNo(),
                        karuta.getCreator(),
                        karuta.getKimariji(),
                        karuta.getTopPhrase().getFirst().getKana(),
                        karuta.getTopPhrase().getFirst().getKanji(),
                        karuta.getTopPhrase().getSecond().getKana(),
                        karuta.getTopPhrase().getSecond().getKanji(),
                        karuta.getTopPhrase().getThird().getKana(),
                        karuta.getTopPhrase().getThird().getKanji(),
                        karuta.getBottomPhrase().getFourth().getKana(),
                        karuta.getBottomPhrase().getFourth().getKanji(),
                        karuta.getBottomPhrase().getFifth().getKana(),
                        karuta.getBottomPhrase().getFifth().getKanji()));
    }
}
