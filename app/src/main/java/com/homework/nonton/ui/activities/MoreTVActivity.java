package com.homework.nonton.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.BuildConfig;
import com.homework.nonton.R;
import com.homework.nonton.ui.adapters.TVAdapterMore;
import com.homework.nonton.databinding.ActivityMoreTvBinding;
import com.homework.nonton.ui.listeners.TVListener;
import com.homework.nonton.models.TVModel;
import com.homework.nonton.viewmodels.TVViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoreTVActivity extends AppCompatActivity implements TVListener {

    private ActivityMoreTvBinding binding;
    private TVViewModel viewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapterMore tvAdapterMore;
    private int currentPage = 1;
    private int totalAvailablePages = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_more_tv);
        initialization();
    }

    private void initialization() {
        binding.rvListMoreTv.setHasFixedSize(true);
        binding.rvListMoreTv.setItemViewCacheSize(20);
        binding.rvListMoreTv.setDrawingCacheEnabled(true);
        binding.rvListMoreTv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        viewModel = new ViewModelProvider(this).get(TVViewModel.class);
        tvAdapterMore = new TVAdapterMore(tvModels, this);
        binding.rvListMoreTv.setAdapter(tvAdapterMore);
        binding.rvListMoreTv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.rvListMoreTv.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getPopularTVShows();
                    }
                }
            }
        });
        binding.ivBackMoreTv.setOnClickListener(view -> onBackPressed());
        binding.ivSearchMoreTv.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), TVSearchActivity.class)));
        getPopularTVShows();
    }

    private void getPopularTVShows() {
        toggleLoading();
        viewModel.getPopularTVShows(BuildConfig.API_KEY, currentPage).observe(this, tvResponse -> {
            toggleLoading();
            if (tvResponse != null) {
                totalAvailablePages = tvResponse.getTotalPages();
                if (tvResponse.getResults() != null) {
                    int oldCount = tvModels.size();
                    tvModels.addAll(tvResponse.getResults());
                    tvAdapterMore.notifyItemRangeInserted(oldCount, tvModels.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (binding.getIsLoading() != null && binding.getIsLoading()) {
                binding.setIsLoading(false);
            } else {
                binding.setIsLoading(true);
            }
        } else {
            if (binding.getIsLoadingMore() != null && binding.getIsLoadingMore()) {
                binding.setIsLoadingMore(false);
            } else {
                binding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVClicked(TVModel tvModel) {
        Intent intent = new Intent(getApplicationContext(), TVDetailsActivity.class);
        intent.putExtra("tv", tvModel);
        startActivity(intent);
    }
}