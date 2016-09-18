package me.rei_m.hyakuninisshu.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import me.rei_m.hyakuninisshu.di.FragmentComponent;

public class BaseFragment extends Fragment implements GraphFragment {

    private FragmentComponent component;

    @Override
    public FragmentComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component = ((GraphActivity) getActivity()).getComponent().fragmentComponent();
    }
}
