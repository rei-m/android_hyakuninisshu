package me.rei_m.hyakuninisshu.viewmodel;

import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbsFragmentViewModel {

    private CompositeDisposable disposable = null;

    @CallSuper
    public void onStart() {

    }

    @CallSuper
    public void onResume() {

    }

    @CallSuper
    public void onPause() {

    }

    @CallSuper
    public void onStop() {
        if (disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }

    protected void registerDisposable(Disposable... disposables) {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        disposable.addAll(disposables);
    }
}
