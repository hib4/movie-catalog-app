package com.homework.nonton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.R;
import com.homework.nonton.adapters.TVAdapter;
import com.homework.nonton.databinding.ActivityMoreTvBinding;
import com.homework.nonton.listeners.TVListener;
import com.homework.nonton.models.TVModel;
import com.homework.nonton.viewmodels.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoreTVActivity extends AppCompatActivity implements TVListener {

    private ActivityMoreTvBinding activityMoreTvBinding;
    private TVViewModel tvViewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapter tvAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMoreTvBinding = DataBindingUtil.setContentView(this, R.layout.activity_more_tv);
        doInitialization();
    }

    private void doInitialization() {
        activityMoreTvBinding.rvListMoreTv.setHasFixedSize(true);
        activityMoreTvBinding.rvListMoreTv.setItemViewCacheSize(20);
        activityMoreTvBinding.rvListMoreTv.setDrawingCacheEnabled(true);
        activityMoreTvBinding.rvListMoreTv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        tvViewModel = new ViewModelProvider(this).get(TVViewModel.class);
        tvAdapter = new TVAdapter(tvModels, this);
        activityMoreTvBinding.rvListMoreTv.setAdapter(tvAdapter);
        activityMoreTvBinding.rvListMoreTv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMoreTvBinding.rvListMoreTv.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getPopularTVShows();
                    }
                }
            }
        });
        getPopularTVShows();
    }

    private void getPopularTVShows() {
        toggleLoading();
        tvViewModel.getPopularTVShows("6336e4208132f6206aa0b05d04b1fda7", currentPage).observe(this, tvResponse -> {
            toggleLoading();
            if (tvResponse != null) {
                totalAvailablePages = tvResponse.getTotalPages();
                if (tvResponse.getResults() != null) {
                    int oldCount = tvModels.size();
                    tvModels.addAll(tvResponse.getResults());
                    tvAdapter.notifyItemRangeInserted(oldCount, tvModels.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityMoreTvBinding.getIsLoading() != null && activityMoreTvBinding.getIsLoading()) {
                activityMoreTvBinding.setIsLoading(false);
            } else {
                activityMoreTvBinding.setIsLoading(true);
            }
        } else {
            if (activityMoreTvBinding.getIsLoadingMore() != null && activityMoreTvBinding.getIsLoadingMore()) {
                activityMoreTvBinding.setIsLoadingMore(false);
            } else {
                activityMoreTvBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVClicked(TVModel tvModel) {
        Intent intent = new Intent(getApplicationContext(), TVDetailsActivity.class);
        intent.putExtra("id", tvModel.getId());
        intent.putExtra("name", tvModel.getName());
        intent.putExtra("original_name", tvModel.getOriginalName());
        intent.putExtra("language", tvModel.getOriginalLanguage());
        intent.putExtra("date", tvModel.getFirstAirDate());
        startActivity(intent);
    }
}