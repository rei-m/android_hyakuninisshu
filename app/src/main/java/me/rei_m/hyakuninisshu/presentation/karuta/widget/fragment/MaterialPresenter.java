package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import me.rei_m.hyakuninisshu.usecase.karuta.DisplayMaterialUsecase;

public class MaterialPresenter implements MaterialContact.Actions {

    private final DisplayMaterialUsecase displayMaterialUsecase;

    private MaterialContact.View view;

    private CompositeDisposable disposable;

    public MaterialPresenter(@NonNull DisplayMaterialUsecase displayMaterialUsecase) {
        this.displayMaterialUsecase = displayMaterialUsecase;
    }

    @Override
    public void onCreate(@NonNull MaterialContact.View view) {
        this.view = view;
    }

    @Override
    public void onResume() {
        disposable = new CompositeDisposable();
        disposable.add(displayMaterialUsecase.execute()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::initialize));
    }

    @Override
    public void onPause() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
