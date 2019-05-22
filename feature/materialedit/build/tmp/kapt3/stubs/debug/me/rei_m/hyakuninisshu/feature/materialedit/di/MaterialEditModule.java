package me.rei_m.hyakuninisshu.feature.materialedit.di;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\"\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/di/MaterialEditModule;", "", "karutaId", "Lme/rei_m/hyakuninisshu/domain/model/karuta/KarutaIdentifier;", "(Lme/rei_m/hyakuninisshu/domain/model/karuta/KarutaIdentifier;)V", "provideMaterialEditViewModelFactory", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditViewModel$Factory;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "store", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditStore;", "actionCreator", "Lme/rei_m/hyakuninisshu/action/material/MaterialActionCreator;", "provideNavigator", "Lme/rei_m/hyakuninisshu/feature/corecomponent/helper/Navigator;", "activity", "Landroidx/appcompat/app/AppCompatActivity;", "materialedit_debug"})
@dagger.Module()
public final class MaterialEditModule {
    private final me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier karutaId = null;
    
    @org.jetbrains.annotations.NotNull()
    @me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope()
    @dagger.Provides()
    public final me.rei_m.hyakuninisshu.feature.corecomponent.helper.Navigator provideNavigator(@org.jetbrains.annotations.NotNull()
    androidx.appcompat.app.AppCompatActivity activity) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope()
    @dagger.Provides()
    public final me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditViewModel.Factory provideMaterialEditViewModelFactory(@org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "vmCoroutineContext")
    kotlin.coroutines.CoroutineContext coroutineContext, @org.jetbrains.annotations.NotNull()
    me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditStore store, @org.jetbrains.annotations.NotNull()
    me.rei_m.hyakuninisshu.action.material.MaterialActionCreator actionCreator) {
        return null;
    }
    
    public MaterialEditModule(@org.jetbrains.annotations.NotNull()
    me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier karutaId) {
        super();
    }
}