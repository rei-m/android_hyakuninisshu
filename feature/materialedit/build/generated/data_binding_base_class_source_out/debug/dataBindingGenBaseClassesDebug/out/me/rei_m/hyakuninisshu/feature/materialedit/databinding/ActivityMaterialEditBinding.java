package me.rei_m.hyakuninisshu.feature.materialedit.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;

public abstract class ActivityMaterialEditBinding extends ViewDataBinding {
  @NonNull
  public final FrameLayout content;

  @NonNull
  public final RelativeLayout root;

  @NonNull
  public final Toolbar toolbar;

  protected ActivityMaterialEditBinding(Object _bindingComponent, View _root, int _localFieldCount,
      FrameLayout content, RelativeLayout root, Toolbar toolbar) {
    super(_bindingComponent, _root, _localFieldCount);
    this.content = content;
    this.root = root;
    this.toolbar = toolbar;
  }

  @NonNull
  public static ActivityMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_material_edit, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<ActivityMaterialEditBinding>inflateInternal(inflater, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.activity_material_edit, root, attachToRoot, component);
  }

  @NonNull
  public static ActivityMaterialEditBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.activity_material_edit, null, false, component)
   */
  @NonNull
  @Deprecated
  public static ActivityMaterialEditBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<ActivityMaterialEditBinding>inflateInternal(inflater, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.activity_material_edit, null, false, component);
  }

  public static ActivityMaterialEditBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static ActivityMaterialEditBinding bind(@NonNull View view, @Nullable Object component) {
    return (ActivityMaterialEditBinding)bind(component, view, me.rei_m.hyakuninisshu.feature.materialedit.R.layout.activity_material_edit);
  }
}
