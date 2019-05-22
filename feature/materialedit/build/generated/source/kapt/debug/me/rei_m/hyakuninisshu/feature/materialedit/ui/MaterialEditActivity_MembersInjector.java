package me.rei_m.hyakuninisshu.feature.materialedit.ui;

import androidx.fragment.app.Fragment;
import dagger.MembersInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.DaggerAppCompatActivity_MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MaterialEditActivity_MembersInjector
    implements MembersInjector<MaterialEditActivity> {
  private final Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider;

  private final Provider<DispatchingAndroidInjector<android.app.Fragment>>
      frameworkFragmentInjectorProvider;

  private final Provider<AdViewObserver> adViewObserverProvider;

  public MaterialEditActivity_MembersInjector(
      Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.app.Fragment>> frameworkFragmentInjectorProvider,
      Provider<AdViewObserver> adViewObserverProvider) {
    this.supportFragmentInjectorProvider = supportFragmentInjectorProvider;
    this.frameworkFragmentInjectorProvider = frameworkFragmentInjectorProvider;
    this.adViewObserverProvider = adViewObserverProvider;
  }

  public static MembersInjector<MaterialEditActivity> create(
      Provider<DispatchingAndroidInjector<Fragment>> supportFragmentInjectorProvider,
      Provider<DispatchingAndroidInjector<android.app.Fragment>> frameworkFragmentInjectorProvider,
      Provider<AdViewObserver> adViewObserverProvider) {
    return new MaterialEditActivity_MembersInjector(
        supportFragmentInjectorProvider, frameworkFragmentInjectorProvider, adViewObserverProvider);
  }

  @Override
  public void injectMembers(MaterialEditActivity instance) {
    DaggerAppCompatActivity_MembersInjector.injectSupportFragmentInjector(
        instance, supportFragmentInjectorProvider.get());
    DaggerAppCompatActivity_MembersInjector.injectFrameworkFragmentInjector(
        instance, frameworkFragmentInjectorProvider.get());
    injectAdViewObserver(instance, adViewObserverProvider.get());
  }

  public static void injectAdViewObserver(
      MaterialEditActivity instance, AdViewObserver adViewObserver) {
    instance.adViewObserver = adViewObserver;
  }
}
