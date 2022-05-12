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

    private ActivityMovieSearchBinding activityMovieSearchBinding;
    private MovieSearchViewModel viewModel;
    private List<MovieModel> movieModels = new ArrayList<>();
    private MovieAdapterMore movieAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieSearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_search);
        doInitialization();
    }

    private void doInitialization() {
        activityMovieSearchBinding.ivBackMovieSearch.setOnClickListener(view -> onBackPressed());
        activityMovieSearchBinding.rvListSearchMovie.setHasFixedSize(true);
        activityMovieSearchBinding.rvListSearchMovie.setItemViewCacheSize(20);
        activityMovieSearchBinding.rvListSearchMovie.setDrawingCacheEnabled(true);
        activityMovieSearchBinding.rvListSearchMovie.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        viewModel = new ViewModelProvider(this).get(MovieSearchViewModel.class);
        movieAdapter = new MovieAdapterMore(movieModels, this);
        activityMovieSearchBinding.rvListSearchMovie.setAdapter(movieAdapter);
        activityMovieSearchBinding.edSearchMovie.addTextChangedListener(new TextWatcher() {
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
        activityMovieSearchBinding.rvListSearchMovie.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMovieSearchBinding.rvListSearchMovie.canScrollVertically(1)) {
                    if (!activityMovieSearchBinding.edSearchMovie.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchMovie(activityMovieSearchBinding.edSearchMovie.getText().toString());
                        }
                    }
                }
            }
        });
        activityMovieSearchBinding.edSearchMovie.requestFocus();
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
            if (activityMovieSearchBinding.getIsLoading() != null && activityMovieSearchBinding.getIsLoading()) {
                activityMovieSearchBinding.setIsLoading(false);
            } else {
                activityMovieSearchBinding.setIsLoading(true);
            }
        } else {
            if (activityMovieSearchBinding.getIsLoadingMore() != null && activityMovieSearchBinding.getIsLoadingMore()) {
                activityMovieSearchBinding.setIsLoadingMore(false);
            } else {
                activityMovieSearchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(getApplicationContext(), MovieDetailsActivity.class);
        intent.putExtra("id", movieModel.getId());
        intent.putExtra("name", movieModel.getTitle());
        intent.putExtra("original_name", movieModel.getOriginalTitle());
        intent.putExtra("language", movieModel.getOriginalLanguage());
        intent.putExtra("date", movieModel.getReleaseDate());
        startActivity(intent);
    }
}