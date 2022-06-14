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

    private ActivityTvSearchBinding binding;
    private TVSearchViewModel viewModel;
    private List<TVModel> tvModels = new ArrayList<>();
    private TVAdapterMore tvAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_search);
        initialization();
    }

    private void initialization() {
        binding.ivBackTvSearch.setOnClickListener(view -> onBackPressed());
        binding.rvListSearchTv.setHasFixedSize(true);
        binding.rvListSearchTv.setItemViewCacheSize(20);
        binding.rvListSearchTv.setDrawingCacheEnabled(true);
        binding.rvListSearchTv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        viewModel = new ViewModelProvider(this).get(TVSearchViewModel.class);
        tvAdapter = new TVAdapterMore(tvModels, this);
        binding.rvListSearchTv.setAdapter(tvAdapter);
        binding.edSearchTv.addTextChangedListener(new TextWatcher() {
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
        binding.rvListSearchTv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.rvListSearchTv.canScrollVertically(1)) {
                    if (!binding.edSearchTv.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTV(binding.edSearchTv.getText().toString());
                        }
                    }
                }
            }
        });
        binding.edSearchTv.requestFocus();
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