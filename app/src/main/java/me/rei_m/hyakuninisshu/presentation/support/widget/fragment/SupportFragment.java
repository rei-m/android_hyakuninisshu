package me.rei_m.hyakuninisshu.presentation.support.widget.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.rei_m.hyakuninisshu.App;
import me.rei_m.hyakuninisshu.BuildConfig;
import me.rei_m.hyakuninisshu.R;
import me.rei_m.hyakuninisshu.databinding.FragmentSupportBinding;
import me.rei_m.hyakuninisshu.presentation.BaseFragment;
import me.rei_m.hyakuninisshu.presentation.manager.AnalyticsManager;

public class SupportFragment extends BaseFragment {

    public static final String TAG = "SupportFragment";

    public static SupportFragment newInstance() {
        return new SupportFragment();
    }

    private FragmentSupportBinding binding;

    public SupportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSupportBinding.inflate(inflater, container, false);
        binding.textVersion.setText(getString(R.string.version, BuildConfig.VERSION_NAME));
        binding.textReview.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_url)));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
        });
        binding.textLicense.setOnClickListener(view -> {
            FragmentManager manager = getFragmentManager();
            if (manager.findFragmentByTag(LicenceDialogFragment.TAG) == null) {
                LicenceDialogFragment dialog = LicenceDialogFragment.newInstance();
                dialog.show(manager, LicenceDialogFragment.TAG);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        AnalyticsManager analyticsManager = ((App) getContext().getApplicationContext()).getAnalyticsManager();
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.SUPPORT);
    }

    @Override
    protected void setupFragmentComponent() {

    }
}
