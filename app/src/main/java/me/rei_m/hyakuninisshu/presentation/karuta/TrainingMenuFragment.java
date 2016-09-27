package me.rei_m.hyakuninisshu.presentation.karuta;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.hyakuninisshu.R;

public class TrainingMenuFragment extends Fragment {

    public TrainingMenuFragment() {
        // Required empty public constructor
    }

    public static TrainingMenuFragment newInstance(String param1, String param2) {
        return new TrainingMenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_training_menu, container, false);
    }
}
