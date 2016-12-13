package me.rei_m.hyakuninisshu.presentation.karuta.widget.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import me.rei_m.hyakuninisshu.presentation.karuta.constant.KarutaConstant;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.MaterialDetailFragment;

public class MaterialDetailPagerAdapter extends FragmentStatePagerAdapter {

    public MaterialDetailPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return MaterialDetailFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return KarutaConstant.NUMBER_OF_KARUTA;
    }
}
