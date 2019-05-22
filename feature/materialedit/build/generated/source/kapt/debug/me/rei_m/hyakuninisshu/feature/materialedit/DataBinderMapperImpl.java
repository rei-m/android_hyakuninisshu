package me.rei_m.hyakuninisshu.feature.materialedit;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Generated;
import me.rei_m.hyakuninisshu.feature.materialedit.databinding.ActivityMaterialEditBindingImpl;
import me.rei_m.hyakuninisshu.feature.materialedit.databinding.DialogConfirmMaterialEditBindingImpl;
import me.rei_m.hyakuninisshu.feature.materialedit.databinding.FragmentMaterialEditBindingImpl;

@Generated("Android Data Binding")
public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYMATERIALEDIT = 1;

  private static final int LAYOUT_DIALOGCONFIRMMATERIALEDIT = 2;

  private static final int LAYOUT_FRAGMENTMATERIALEDIT = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.rei_m.hyakuninisshu.feature.materialedit.R.layout.activity_material_edit, LAYOUT_ACTIVITYMATERIALEDIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.rei_m.hyakuninisshu.feature.materialedit.R.layout.dialog_confirm_material_edit, LAYOUT_DIALOGCONFIRMMATERIALEDIT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(me.rei_m.hyakuninisshu.feature.materialedit.R.layout.fragment_material_edit, LAYOUT_FRAGMENTMATERIALEDIT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYMATERIALEDIT: {
          if ("layout/activity_material_edit_0".equals(tag)) {
            return new ActivityMaterialEditBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_material_edit is invalid. Received: " + tag);
        }
        case  LAYOUT_DIALOGCONFIRMMATERIALEDIT: {
          if ("layout/dialog_confirm_material_edit_0".equals(tag)) {
            return new DialogConfirmMaterialEditBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for dialog_confirm_material_edit is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTMATERIALEDIT: {
          if ("layout/fragment_material_edit_0".equals(tag)) {
            return new FragmentMaterialEditBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_material_edit is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(13);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "thirdPhraseKana");
      sKeys.put(2, "fifthPhraseKanji");
      sKeys.put(3, "secondPhraseKanji");
      sKeys.put(4, "fourthPhraseKanji");
      sKeys.put(5, "firstPhraseKanji");
      sKeys.put(6, "thirdPhraseKanji");
      sKeys.put(7, "viewModel");
      sKeys.put(8, "fifthPhraseKana");
      sKeys.put(9, "firstPhraseKana");
      sKeys.put(10, "secondPhraseKana");
      sKeys.put(11, "fourthPhraseKana");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/activity_material_edit_0", me.rei_m.hyakuninisshu.feature.materialedit.R.layout.activity_material_edit);
      sKeys.put("layout/dialog_confirm_material_edit_0", me.rei_m.hyakuninisshu.feature.materialedit.R.layout.dialog_confirm_material_edit);
      sKeys.put("layout/fragment_material_edit_0", me.rei_m.hyakuninisshu.feature.materialedit.R.layout.fragment_material_edit);
    }
  }
}
