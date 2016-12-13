package me.rei_m.hyakuninisshu.usecase.karuta.impl;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Single;
import me.rei_m.hyakuninisshu.domain.karuta.repository.KarutaRepository;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.MaterialViewModel;
import me.rei_m.hyakuninisshu.presentation.utilitty.KarutaDisplayUtil;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialUsecase;

import static me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant.SPACE;

public class DisplayMaterialUsecaseImpl implements DisplayMaterialUsecase {

    private final Context context;

    private final KarutaRepository karutaRepository;

    public DisplayMaterialUsecaseImpl(@NonNull Context context,
                                      @NonNull KarutaRepository karutaRepository) {
        this.context = context;
        this.karutaRepository = karutaRepository;
    }

    @Override
    public Single<MaterialViewModel> execute() {
        return karutaRepository.asEntityList().flatMap(karutaList -> Observable.fromIterable(karutaList)
                .map(karuta -> {

                    String topPhrase = karuta.getTopPhrase().getFirst().getKanji() + SPACE +
                            karuta.getTopPhrase().getSecond().getKanji() + SPACE +
                            karuta.getTopPhrase().getThird().getKanji();

                    String bottomPhrase = karuta.getBottomPhrase().getFourth().getKanji() + SPACE +
                            karuta.getBottomPhrase().getFifth().getKanji();

                    int karutaNo = (int) karuta.getIdentifier().getValue();

                    return new MaterialViewModel.KarutaViewModel(
                            karutaNo,
                            KarutaDisplayUtil.convertNumberToString(context, karutaNo),
                            karuta.getImageNo(),
                            karuta.getCreator(),
                            topPhrase,
                            bottomPhrase);
                })
                .toList()
                .map(MaterialViewModel::new));
    }
}
