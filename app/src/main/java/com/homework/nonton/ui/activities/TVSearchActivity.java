package com.homework.nonton.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.BuildConfig;
import com.homework.nonton.R;
import com.homework.nonton.databinding.ActivityTvSearchBinding;
import com.homework.nonton.models.TVModel;
import com.homework.nonton.ui.adapters.TVAdapterMore;
import com.homework.nonton.ui.listeners.TVListener;
import com.homework.nonton.viewmodels.TVSearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TVSearchActivity extends AppCompatActivity implements TVListener {

    private ActivityTvSearchBinding activityTvSearchBinding;
    private TVSearchViewModel viewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapterMore tvAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_search);
        doInitialization();
    }

    private void doInitialization() {
        activityTvSearchBinding.ivBackTvSearch.setOnClickListener(view -> onBackPressed());
        activityTvSearchBinding.rvListSearchTv.setHasFixedSize(true);
        activityTvSearchBinding.rvListSearchTv.setItemViewCacheSize(20);
        activityTvSearchBinding.rvListSearchTv.setDrawingCacheEnabled(true);
        activityTvSearchBinding.rvListSearchTv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        viewModel = new ViewModelProvider(this).get(TVSearchViewModel.class);
        tvAdapter = new TVAdapterMore(tvModels, this);
        activityTvSearchBinding.rvListSearchTv.setAdapter(tvAdapter);
        activityTvSearchBinding.edSearchTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().trim().isEmpty()) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            new Handler(Looper.getMainLooper()).post(() -> {
                                currentPage = 1;
                                totalAvailablePages = 1;
                                searchTV(editable.toString());
                            });
                        }
                    }, 800);
                } else {
                    tvModels.clear();
                    tvAdapter.notifyDataSetChanged();
                }
            }
        });
        activityTvSearchBinding.rvListSearchTv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityTvSearchBinding.rvListSearchTv.canScrollVertically(1)) {
                    if (!activityTvSearchBinding.edSearchTv.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTV(activityTvSearchBinding.edSearchTv.getText().toString());
                        }
                    }
                }
            }
        });
        activityTvSearchBinding.edSearchTv.requestFocus();
    }

    private void searchTV(String query) {
        toggleLoading();
        viewModel.searchTV(BuildConfig.API_KEY, currentPage, query).observe(this, tvResponse -> {
            toggleLoading();
            if (tvResponse != null) {
                totalAvailablePages = tvResponse.getTotalPages();
                if (tvResponse.getResults() != null) {
                    int oldCount = tvModels.size();
                    tvModels.addAll(tvResponse.getResults());
                    tvAdapter.notifyItemRangeChanged(oldCount, tvModels.size());
                }
            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (activityTvSearchBinding.getIsLoading() != null && activityTvSearchBinding.getIsLoading()) {
                activityTvSearchBinding.setIsLoading(false);
            } else {
                activityTvSearchBinding.setIsLoading(true);
            }
        } else {
            if (activityTvSearchBinding.getIsLoadingMore() != null && activityTvSearchBinding.getIsLoadingMore()) {
                activityTvSearchBinding.setIsLoadingMore(false);
            } else {
                activityTvSearchBinding.setIsLoadingMore(true);
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