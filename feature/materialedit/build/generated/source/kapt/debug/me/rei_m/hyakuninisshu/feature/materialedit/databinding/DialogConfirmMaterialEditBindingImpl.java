package me.rei_m.hyakuninisshu.feature.materialedit.databinding;
import me.rei_m.hyakuninisshu.feature.materialedit.R;
import me.rei_m.hyakuninisshu.feature.materialedit.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
@javax.annotation.Generated("Android Data Binding")
public class DialogConfirmMaterialEditBindingImpl extends DialogConfirmMaterialEditBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.ScrollView mboundView0;
    @NonNull
    private final android.widget.TextView mboundView1;
    @NonNull
    private final android.widget.TextView mboundView10;
    @NonNull
    private final android.widget.TextView mboundView2;
    @NonNull
    private final android.widget.TextView mboundView3;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    @NonNull
    private final android.widget.TextView mboundView8;
    @NonNull
    private final android.widget.TextView mboundView9;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogConfirmMaterialEditBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds));
    }
    private DialogConfirmMaterialEditBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            );
        this.mboundView0 = (android.widget.ScrollView) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView10 = (android.widget.TextView) bindings[10];
        this.mboundView10.setTag(null);
        this.mboundView2 = (android.widget.TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (android.widget.TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        this.mboundView9 = (android.widget.TextView) bindings[9];
        this.mboundView9.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x400L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.firstPhraseKana == variableId) {
            setFirstPhraseKana((java.lang.String) variable);
        }
        else if (BR.fifthPhraseKana == variableId) {
            setFifthPhraseKana((java.lang.String) variable);
        }
        else if (BR.secondPhraseKanji == variableId) {
            setSecondPhraseKanji((java.lang.String) variable);
        }
        else if (BR.fourthPhraseKanji == variableId) {
            setFourthPhraseKanji((java.lang.String) variable);
        }
        else if (BR.firstPhraseKanji == variableId) {
            setFirstPhraseKanji((java.lang.String) variable);
        }
        else if (BR.thirdPhraseKanji == variableId) {
            setThirdPhraseKanji((java.lang.String) variable);
        }
        else if (BR.secondPhraseKana == variableId) {
            setSecondPhraseKana((java.lang.String) variable);
        }
        else if (BR.thirdPhraseKana == variableId) {
            setThirdPhraseKana((java.lang.String) variable);
        }
        else if (BR.fourthPhraseKana == variableId) {
            setFourthPhraseKana((java.lang.String) variable);
        }
        else if (BR.fifthPhraseKanji == variableId) {
            setFifthPhraseKanji((java.lang.String) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setFirstPhraseKana(@Nullable java.lang.String FirstPhraseKana) {
        this.mFirstPhraseKana = FirstPhraseKana;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.firstPhraseKana);
        super.requestRebind();
    }
    public void setFifthPhraseKana(@Nullable java.lang.String FifthPhraseKana) {
        this.mFifthPhraseKana = FifthPhraseKana;
        synchronized(this) {
            mDirtyFlags |= 0x2L;
        }
        notifyPropertyChanged(BR.fifthPhraseKana);
        super.requestRebind();
    }
    public void setSecondPhraseKanji(@Nullable java.lang.String SecondPhraseKanji) {
        this.mSecondPhraseKanji = SecondPhraseKanji;
        synchronized(this) {
            mDirtyFlags |= 0x4L;
        }
        notifyPropertyChanged(BR.secondPhraseKanji);
        super.requestRebind();
    }
    public void setFourthPhraseKanji(@Nullable java.lang.String FourthPhraseKanji) {
        this.mFourthPhraseKanji = FourthPhraseKanji;
        synchronized(this) {
            mDirtyFlags |= 0x8L;
        }
        notifyPropertyChanged(BR.fourthPhraseKanji);
        super.requestRebind();
    }
    public void setFirstPhraseKanji(@Nullable java.lang.String FirstPhraseKanji) {
        this.mFirstPhraseKanji = FirstPhraseKanji;
        synchronized(this) {
            mDirtyFlags |= 0x10L;
        }
        notifyPropertyChanged(BR.firstPhraseKanji);
        super.requestRebind();
    }
    public void setThirdPhraseKanji(@Nullable java.lang.String ThirdPhraseKanji) {
        this.mThirdPhraseKanji = ThirdPhraseKanji;
        synchronized(this) {
            mDirtyFlags |= 0x20L;
        }
        notifyPropertyChanged(BR.thirdPhraseKanji);
        super.requestRebind();
    }
    public void setSecondPhraseKana(@Nullable java.lang.String SecondPhraseKana) {
        this.mSecondPhraseKana = SecondPhraseKana;
        synchronized(this) {
            mDirtyFlags |= 0x40L;
        }
        notifyPropertyChanged(BR.secondPhraseKana);
        super.requestRebind();
    }
    public void setThirdPhraseKana(@Nullable java.lang.String ThirdPhraseKana) {
        this.mThirdPhraseKana = ThirdPhraseKana;
        synchronized(this) {
            mDirtyFlags |= 0x80L;
        }
        notifyPropertyChanged(BR.thirdPhraseKana);
        super.requestRebind();
    }
    public void setFourthPhraseKana(@Nullable java.lang.String FourthPhraseKana) {
        this.mFourthPhraseKana = FourthPhraseKana;
        synchronized(this) {
            mDirtyFlags |= 0x100L;
        }
        notifyPropertyChanged(BR.fourthPhraseKana);
        super.requestRebind();
    }
    public void setFifthPhraseKanji(@Nullable java.lang.String FifthPhraseKanji) {
        this.mFifthPhraseKanji = FifthPhraseKanji;
        synchronized(this) {
            mDirtyFlags |= 0x200L;
        }
        notifyPropertyChanged(BR.fifthPhraseKanji);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String firstPhraseKana = mFirstPhraseKana;
        java.lang.String fifthPhraseKana = mFifthPhraseKana;
        java.lang.String secondPhraseKanji = mSecondPhraseKanji;
        java.lang.String fourthPhraseKanji = mFourthPhraseKanji;
        java.lang.String firstPhraseKanji = mFirstPhraseKanji;
        java.lang.String thirdPhraseKanji = mThirdPhraseKanji;
        java.lang.String secondPhraseKana = mSecondPhraseKana;
        java.lang.String thirdPhraseKana = mThirdPhraseKana;
        java.lang.String fourthPhraseKana = mFourthPhraseKana;
        java.lang.String fifthPhraseKanji = mFifthPhraseKanji;

        if ((dirtyFlags & 0x401L) != 0) {
        }
        if ((dirtyFlags & 0x402L) != 0) {
        }
        if ((dirtyFlags & 0x404L) != 0) {
        }
        if ((dirtyFlags & 0x408L) != 0) {
        }
        if ((dirtyFlags & 0x410L) != 0) {
        }
        if ((dirtyFlags & 0x420L) != 0) {
        }
        if ((dirtyFlags & 0x440L) != 0) {
        }
        if ((dirtyFlags & 0x480L) != 0) {
        }
        if ((dirtyFlags & 0x500L) != 0) {
        }
        if ((dirtyFlags & 0x600L) != 0) {
        }
        // batch finished
        if ((dirtyFlags & 0x410L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView1, firstPhraseKanji);
        }
        if ((dirtyFlags & 0x402L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView10, fifthPhraseKana);
        }
        if ((dirtyFlags & 0x401L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView2, firstPhraseKana);
        }
        if ((dirtyFlags & 0x404L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView3, secondPhraseKanji);
        }
        if ((dirtyFlags & 0x440L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, secondPhraseKana);
        }
        if ((dirtyFlags & 0x420L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, thirdPhraseKanji);
        }
        if ((dirtyFlags & 0x480L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, thirdPhraseKana);
        }
        if ((dirtyFlags & 0x408L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView7, fourthPhraseKanji);
        }
        if ((dirtyFlags & 0x500L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, fourthPhraseKana);
        }
        if ((dirtyFlags & 0x600L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView9, fifthPhraseKanji);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): firstPhraseKana
        flag 1 (0x2L): fifthPhraseKana
        flag 2 (0x3L): secondPhraseKanji
        flag 3 (0x4L): fourthPhraseKanji
        flag 4 (0x5L): firstPhraseKanji
        flag 5 (0x6L): thirdPhraseKanji
        flag 6 (0x7L): secondPhraseKana
        flag 7 (0x8L): thirdPhraseKana
        flag 8 (0x9L): fourthPhraseKana
        flag 9 (0xaL): fifthPhraseKanji
        flag 10 (0xbL): null
    flag mapping end*/
    //end
}