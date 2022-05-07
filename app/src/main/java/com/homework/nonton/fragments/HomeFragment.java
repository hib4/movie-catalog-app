package com.homework.nonton.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.homework.nonton.activities.MoreTVActivity;
import com.homework.nonton.activities.TVDetailsActivity;
import com.homework.nonton.adapters.TVAdapterMain;
import com.homework.nonton.databinding.FragmentHomeBinding;
import com.homework.nonton.listeners.TVListener;
import com.homework.nonton.models.TVModel;
import com.homework.nonton.viewmodels.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements TVListener {

    private FragmentHomeBinding fragmentHomeBinding;
    private TVViewModel tvViewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapterMain tvAdapterMain;

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);

        doInitialization();

        return fragmentHomeBinding.getRoot();
    }

    private void doInitialization() {
        fragmentHomeBinding.rvListMain.setHasFixedSize(true);
        fragmentHomeBinding.rvListMain.setItemViewCacheSize(20);
        fragmentHomeBinding.rvListMain.setDrawingCacheEnabled(true);
        fragmentHomeBinding.rvListMain.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        tvViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        tvAdapterMain = new TVAdapterMain(tvModels, this);
        fragmentHomeBinding.rvListMain.setAdapter(tvAdapterMain);
        fragmentHomeBinding.ivMoreMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MoreTVActivity.class);
                startActivity(intent);
            }
        });
        getPopularTVShows();
    }

    private void getPopularTVShows() {
        fragmentHomeBinding.rlMain.setVisibility(View.GONE);
        fragmentHomeBinding.setIsLoading(true);
        tvViewModel.getPopularTVShows("6336e4208132f6206aa0b05d04b1fda7", 1).observe(getViewLifecycleOwner(), tvResponse -> {
            fragmentHomeBinding.rlMain.setVisibility(View.VISIBLE);
            fragmentHomeBinding.setIsLoading(false);
            if (tvResponse != null) {
                if (tvResponse.getResults() != null) {
                    tvModels.addAll(tvResponse.getResults());
                    tvAdapterMain.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onTVClicked(TVModel tvModel) {
        Intent intent = new Intent(getContext(), TVDetailsActivity.class);
        intent.putExtra("id", tvModel.getId());
        intent.putExtra("name", tvModel.getName());
        intent.putExtra("original_name", tvModel.getOriginalName());
        intent.putExtra("language", tvModel.getOriginalLanguage());
        intent.putExtra("date", tvModel.getFirstAirDate());
        startActivity(intent);
    }
}