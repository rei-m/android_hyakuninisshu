package me.rei_m.hyakuninisshu.feature.materialedit.di;

import androidx.appcompat.app.AppCompatActivity;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.Navigator;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MaterialEditModule_ProvideNavigatorFactory implements Factory<Navigator> {
  private final MaterialEditModule module;

  private final Provider<AppCompatActivity> activityProvider;

  public MaterialEditModule_ProvideNavigatorFactory(
      MaterialEditModule module, Provider<AppCompatActivity> activityProvider) {
    this.module = module;
    this.activityProvider = activityProvider;
  }

  @Override
  public Navigator get() {
    return provideInstance(module, activityProvider);
  }

  public static Navigator provideInstance(
      MaterialEditModule module, Provider<AppCompatActivity> activityProvider) {
    return proxyProvideNavigator(module, activityProvider.get());
  }

  public static MaterialEditModule_ProvideNavigatorFactory create(
      MaterialEditModule module, Provider<AppCompatActivity> activityProvider) {
    return new MaterialEditModule_ProvideNavigatorFactory(module, activityProvider);
  }

  public static Navigator proxyProvideNavigator(
      MaterialEditModule instance, AppCompatActivity activity) {
    return Preconditions.checkNotNull(
        instance.provideNavigator(activity),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
