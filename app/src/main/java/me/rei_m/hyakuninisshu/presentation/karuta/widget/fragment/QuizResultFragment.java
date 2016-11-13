package me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.rei_m.hyakuninisshu.component.HasComponent;
import me.rei_m.hyakuninisshu.databinding.FragmentQuizResultBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.karuta.viewmodel.QuizResultViewModel;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.component.QuizResultFragmentComponent;
import me.rei_m.hyakuninisshu.presentation.karuta.widget.fragment.module.QuizResultFragmentModule;

public class QuizResultFragment extends BaseFragment implements QuizResultContact.View {

    public static QuizResultFragment newInstance() {
        QuizResultFragment fragment = new QuizResultFragment();
        return fragment;
    }
    
    @Inject
    QuizResultContact.Actions presenter;

    private FragmentQuizResultBinding binding;

    private OnFragmentInteractionListener mListener;

    public QuizResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate(this);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        presenter.onCreateView();
        binding = FragmentQuizResultBinding.inflate(inflater, container, false);
        binding.setPresenter(presenter);
        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void setupFragmentComponent() {
        ((HasComponent<Injector>) getActivity()).getComponent()
                .plus(new QuizResultFragmentModule(getContext())).inject(this);
    }

    @Override
    public void initialize(QuizResultViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    public interface Injector {
        QuizResultFragmentComponent plus(QuizResultFragmentModule fragmentModule);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
