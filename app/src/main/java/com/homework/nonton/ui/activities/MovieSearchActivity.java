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
import com.homework.nonton.databinding.ActivityMovieSearchBinding;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.ui.adapters.MovieAdapterMore;
import com.homework.nonton.ui.listeners.MovieListener;
import com.homework.nonton.viewmodels.MovieSearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MovieSearchActivity extends AppCompatActivity implements MovieListener {

    private ActivityMovieSearchBinding binding;
    private MovieSearchViewModel viewModel;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapterMore movieAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_search);
        initialization();
    }

    private void initialization() {
        binding.ivBackMovieSearch.setOnClickListener(view -> onBackPressed());
        binding.rvListSearchMovie.setHasFixedSize(true);
        binding.rvListSearchMovie.setItemViewCacheSize(20);
        binding.rvListSearchMovie.setDrawingCacheEnabled(true);
        binding.rvListSearchMovie.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        viewModel = new ViewModelProvider(this).get(MovieSearchViewModel.class);
        movieAdapter = new MovieAdapterMore(movieModels, this);
        binding.rvListSearchMovie.setAdapter(movieAdapter);
        binding.edSearchMovie.addTextChangedListener(new TextWatcher() {
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
                                searchMovie(editable.toString());
                            });
                        }
                    }, 800);
                } else {
                    movieModels.clear();
                    movieAdapter.notifyDataSetChanged();
                }
            }
        });
        binding.rvListSearchMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.rvListSearchMovie.canScrollVertically(1)) {
                    if (!binding.edSearchMovie.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchMovie(binding.edSearchMovie.getText().toString());
                        }
                    }
                }
            }
        });
        binding.edSearchMovie.requestFocus();
    }

    private void searchMovie(String query) {
        toggleLoading();
        viewModel.searchMovie(BuildConfig.API_KEY, query, currentPage).observe(this, movieResponse -> {
            toggleLoading();
            if (movieResponse != null) {
                totalAvailablePages = movieResponse.getTotalPages();
                if (movieResponse.getResults() != null) {
                    int oldCount = movieModels.size();
                    movieModels.addAll(movieResponse.getResults());
                    movieAdapter.notifyItemRangeChanged(oldCount, movieModels.size());
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
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("movie", movieModel);
        startActivity(intent);
    }
}