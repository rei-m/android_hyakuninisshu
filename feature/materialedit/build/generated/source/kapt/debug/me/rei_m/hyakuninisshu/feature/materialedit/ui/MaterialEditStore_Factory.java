package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.rei_m.hyakuninisshu.action.Dispatcher;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MaterialEditStore_Factory implements Factory<MaterialEditStore> {
  private final Provider<Dispatcher> dispatcherProvider;

  public MaterialEditStore_Factory(Provider<Dispatcher> dispatcherProvider) {
    this.dispatcherProvider = dispatcherProvider;
  }

  @Override
  public MaterialEditStore get() {
    return provideInstance(dispatcherProvider);
  }

  public static MaterialEditStore provideInstance(Provider<Dispatcher> dispatcherProvider) {
    return new MaterialEditStore(dispatcherProvider.get());
  }

  public static MaterialEditStore_Factory create(Provider<Dispatcher> dispatcherProvider) {
    return new MaterialEditStore_Factory(dispatcherProvider);
  }

  public static MaterialEditStore newMaterialEditStore(Dispatcher dispatcher) {
    return new MaterialEditStore(dispatcher);
  }
}
