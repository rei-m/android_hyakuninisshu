package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0019\u0010\u0010\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u001d\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000f\u00a8\u0006\u0014"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditStore;", "Lme/rei_m/hyakuninisshu/feature/corecomponent/flux/Store;", "dispatcher", "Lme/rei_m/hyakuninisshu/action/Dispatcher;", "(Lme/rei_m/hyakuninisshu/action/Dispatcher;)V", "_completeEditEvent", "Landroidx/lifecycle/MutableLiveData;", "Lme/rei_m/hyakuninisshu/feature/corecomponent/flux/Event;", "", "_karuta", "Lme/rei_m/hyakuninisshu/domain/model/karuta/Karuta;", "_unhandledErrorEvent", "completeEditEvent", "Landroidx/lifecycle/LiveData;", "getCompleteEditEvent", "()Landroidx/lifecycle/LiveData;", "karuta", "getKaruta", "unhandledErrorEvent", "getUnhandledErrorEvent", "materialedit_debug"})
@me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope()
public final class MaterialEditStore extends me.rei_m.hyakuninisshu.feature.corecomponent.flux.Store {
    private final androidx.lifecycle.MutableLiveData<me.rei_m.hyakuninisshu.domain.model.karuta.Karuta> _karuta = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<me.rei_m.hyakuninisshu.domain.model.karuta.Karuta> karuta = null;
    private final androidx.lifecycle.MutableLiveData<me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event<kotlin.Unit>> _completeEditEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event<kotlin.Unit>> completeEditEvent = null;
    private final androidx.lifecycle.MutableLiveData<me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event<kotlin.Unit>> _unhandledErrorEvent = null;
    @org.jetbrains.annotations.NotNull()
    private final androidx.lifecycle.LiveData<me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event<kotlin.Unit>> unhandledErrorEvent = null;
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<me.rei_m.hyakuninisshu.domain.model.karuta.Karuta> getKaruta() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event<kotlin.Unit>> getCompleteEditEvent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.lifecycle.LiveData<me.rei_m.hyakuninisshu.feature.corecomponent.flux.Event<kotlin.Unit>> getUnhandledErrorEvent() {
        return null;
    }
    
    @javax.inject.Inject()
    public MaterialEditStore(@org.jetbrains.annotations.NotNull()
    me.rei_m.hyakuninisshu.action.Dispatcher dispatcher) {
        super();
    }
}