package me.rei_m.hyakuninisshu.feature.materialedit.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import kotlin.coroutines.CoroutineContext;
import me.rei_m.hyakuninisshu.action.material.MaterialActionCreator;
import me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditStore;
import me.rei_m.hyakuninisshu.feature.materialedit.ui.MaterialEditViewModel;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MaterialEditModule_ProvideMaterialEditViewModelFactoryFactory
    implements Factory<MaterialEditViewModel.Factory> {
  private final MaterialEditModule module;

  private final Provider<CoroutineContext> coroutineContextProvider;

  private final Provider<MaterialEditStore> storeProvider;

  private final Provider<MaterialActionCreator> actionCreatorProvider;

  public MaterialEditModule_ProvideMaterialEditViewModelFactoryFactory(
      MaterialEditModule module,
      Provider<CoroutineContext> coroutineContextProvider,
      Provider<MaterialEditStore> storeProvider,
      Provider<MaterialActionCreator> actionCreatorProvider) {
    this.module = module;
    this.coroutineContextProvider = coroutineContextProvider;
    this.storeProvider = storeProvider;
    this.actionCreatorProvider = actionCreatorProvider;
  }

  @Override
  public MaterialEditViewModel.Factory get() {
    return provideInstance(module, coroutineContextProvider, storeProvider, actionCreatorProvider);
  }

  public static MaterialEditViewModel.Factory provideInstance(
      MaterialEditModule module,
      Provider<CoroutineContext> coroutineContextProvider,
      Provider<MaterialEditStore> storeProvider,
      Provider<MaterialActionCreator> actionCreatorProvider) {
    return proxyProvideMaterialEditViewModelFactory(
        module, coroutineContextProvider.get(), storeProvider.get(), actionCreatorProvider.get());
  }

  public static MaterialEditModule_ProvideMaterialEditViewModelFactoryFactory create(
      MaterialEditModule module,
      Provider<CoroutineContext> coroutineContextProvider,
      Provider<MaterialEditStore> storeProvider,
      Provider<MaterialActionCreator> actionCreatorProvider) {
    return new MaterialEditModule_ProvideMaterialEditViewModelFactoryFactory(
        module, coroutineContextProvider, storeProvider, actionCreatorProvider);
  }

  public static MaterialEditViewModel.Factory proxyProvideMaterialEditViewModelFactory(
      MaterialEditModule instance,
      CoroutineContext coroutineContext,
      MaterialEditStore store,
      MaterialActionCreator actionCreator) {
    return Preconditions.checkNotNull(
        instance.provideMaterialEditViewModelFactory(coroutineContext, store, actionCreator),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
