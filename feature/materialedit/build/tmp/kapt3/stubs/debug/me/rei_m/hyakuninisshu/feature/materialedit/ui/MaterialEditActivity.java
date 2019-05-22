package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000  2\u00020\u00012\u00020\u00022\u00020\u0003:\u0003 !\"B\u0005\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0015H\u0016J\u0012\u0010\u0017\u001a\u00020\u00152\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0014J\b\u0010\u001a\u001a\u00020\u0015H\u0016J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0015H\u0002R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082.\u00a2\u0006\u0002\n\u0000R#\u0010\r\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006#"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity;", "Ldagger/android/support/DaggerAppCompatActivity;", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditFragment$OnFragmentInteractionListener;", "Lme/rei_m/hyakuninisshu/feature/corecomponent/widget/dialog/AlertDialogFragment$OnDialogInteractionListener;", "()V", "adViewObserver", "Lme/rei_m/hyakuninisshu/feature/corecomponent/widget/ad/AdViewObserver;", "getAdViewObserver", "()Lme/rei_m/hyakuninisshu/feature/corecomponent/widget/ad/AdViewObserver;", "setAdViewObserver", "(Lme/rei_m/hyakuninisshu/feature/corecomponent/widget/ad/AdViewObserver;)V", "binding", "Lme/rei_m/hyakuninisshu/feature/materialedit/databinding/ActivityMaterialEditBinding;", "karutaId", "Lme/rei_m/hyakuninisshu/domain/model/karuta/KarutaIdentifier;", "kotlin.jvm.PlatformType", "getKarutaId", "()Lme/rei_m/hyakuninisshu/domain/model/karuta/KarutaIdentifier;", "karutaId$delegate", "Lkotlin/Lazy;", "onAlertNegativeClick", "", "onAlertPositiveClick", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onError", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "setupAd", "Companion", "Module", "Subcomponent", "materialedit_debug"})
public final class MaterialEditActivity extends dagger.android.support.DaggerAppCompatActivity implements me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditFragment.OnFragmentInteractionListener, me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment.OnDialogInteractionListener {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver adViewObserver;
    private me.rei_m.hyakuninisshu.feature.materialedit.databinding.ActivityMaterialEditBinding binding;
    private final kotlin.Lazy karutaId$delegate = null;
    private static final java.lang.String ARG_KARUTA_ID = "karutaId";
    public static final me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver getAdViewObserver() {
        return null;
    }
    
    public final void setAdViewObserver(@org.jetbrains.annotations.NotNull()
    me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver p0) {
    }
    
    private final me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier getKarutaId() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
    
    @java.lang.Override()
    public void onError() {
    }
    
    @java.lang.Override()
    public void onAlertPositiveClick() {
    }
    
    @java.lang.Override()
    public void onAlertNegativeClick() {
    }
    
    private final void setupAd() {
    }
    
    public MaterialEditActivity() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0003\u00a8\u0006\u0004"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity$Subcomponent;", "Ldagger/android/AndroidInjector;", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity;", "Builder", "materialedit_debug"})
    @dagger.Subcomponent(modules = {me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule.class, me.rei_m.hyakuninisshu.feature.materialedit.di.MaterialEditModule.class, me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditFragment.Module.class})
    @me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope()
    public static abstract interface Subcomponent extends dagger.android.AndroidInjector<me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity> {
        
        @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\'\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0002H\u0016\u00a8\u0006\f"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity$Subcomponent$Builder;", "Ldagger/android/AndroidInjector$Builder;", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity;", "()V", "activityModule", "module", "Lme/rei_m/hyakuninisshu/feature/corecomponent/di/ActivityModule;", "materialEditModule", "Lme/rei_m/hyakuninisshu/feature/materialedit/di/MaterialEditModule;", "seedInstance", "", "instance", "materialedit_debug"})
        @dagger.Subcomponent.Builder()
        public static abstract class Builder extends dagger.android.AndroidInjector.Builder<me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity> {
            
            @org.jetbrains.annotations.NotNull()
            public abstract me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity.Subcomponent.Builder activityModule(@org.jetbrains.annotations.NotNull()
            me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule module);
            
            @org.jetbrains.annotations.NotNull()
            public abstract me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity.Subcomponent.Builder materialEditModule(@org.jetbrains.annotations.NotNull()
            me.rei_m.hyakuninisshu.feature.materialedit.di.MaterialEditModule module);
            
            @java.lang.Override()
            public void seedInstance(@org.jetbrains.annotations.NotNull()
            me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity instance) {
            }
            
            public Builder() {
                super();
            }
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\'\u00a8\u0006\b"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity$Module;", "", "()V", "bind", "Ldagger/android/AndroidInjector$Factory;", "Landroid/app/Activity;", "builder", "Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity$Subcomponent$Builder;", "materialedit_debug"})
    @dagger.Module(subcomponents = {me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity.Subcomponent.class})
    public static abstract class Module {
        
        @org.jetbrains.annotations.NotNull()
        @dagger.android.ActivityKey(value = me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity.class)
        @dagger.multibindings.IntoMap()
        @dagger.Binds()
        public abstract dagger.android.AndroidInjector.Factory<? extends android.app.Activity> bind(@org.jetbrains.annotations.NotNull()
        me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditActivity.Subcomponent.Builder builder);
        
        public Module() {
            super();
        }
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lme/rei_m/hyakuninisshu/feature/materialedit/ui/MaterialEditActivity$Companion;", "", "()V", "ARG_KARUTA_ID", "", "createIntent", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "karutaId", "Lme/rei_m/hyakuninisshu/domain/model/karuta/KarutaIdentifier;", "materialedit_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.Intent createIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier karutaId) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}