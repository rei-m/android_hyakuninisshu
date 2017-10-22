/*
 * Copyright (c) 2017. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */

package me.rei_m.hyakuninisshu.viewmodel;

import android.support.annotation.CallSuper;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class AbsActivityViewModel {

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

    public void registerDisposable(Disposable... disposables) {
        if (disposable == null) {
            disposable = new CompositeDisposable();
        }
        disposable.addAll(disposables);
    }
}
