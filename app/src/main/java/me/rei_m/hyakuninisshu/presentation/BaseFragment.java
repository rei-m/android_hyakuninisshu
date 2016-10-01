package me.rei_m.hyakuninisshu.presentation;

import android.support.v4.app.Fragment;

import me.rei_m.hyakuninisshu.di.FragmentComponent;

public class BaseFragment extends Fragment implements GraphFragment {

    private FragmentComponent component;

    @Override
    public FragmentComponent getComponent() {
        if (component == null) {
            component = ((GraphActivity) getActivity()).getComponent().fragmentComponent();
        }
        return component;
    }
}
