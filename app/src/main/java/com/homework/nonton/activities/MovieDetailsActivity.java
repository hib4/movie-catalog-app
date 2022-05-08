package com.homework.nonton.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.homework.nonton.R;
import com.homework.nonton.databinding.ActivityMovieDetailsBinding;
import com.homework.nonton.models.GenresItemMovie;
import com.homework.nonton.viewmodels.MovieDetailsViewModel;

import java.util.Locale;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding activityMovieDetailsBinding;
    private MovieDetailsViewModel movieDetailsViewModel;
    private GenresItemMovie genresItemMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMovieDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        doInitialization();
    }

    private void doInitialization() {
        movieDetailsViewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        activityMovieDetailsBinding.ivBackMovieDetails.setOnClickListener(view -> onBackPressed());
        getMovieDetails();
    }

    private void getMovieDetails() {
        activityMovieDetailsBinding.setIsLoading(true);
        String ID = String.valueOf(getIntent().getIntExtra("id", -1));
        movieDetailsViewModel.getPopularMovieDetails(ID, "6336e4208132f6206aa0b05d04b1fda7").observe(
                this, movieDetailsResponse -> {
                    activityMovieDetailsBinding.setIsLoading(false);
                    activityMovieDetailsBinding.setImageURL(movieDetailsResponse.getPosterPath());
                    activityMovieDetailsBinding.setImageBdURL(movieDetailsResponse.getBackdropPath());
                    activityMovieDetailsBinding.setOverview(movieDetailsResponse.getOverview());
                    activityMovieDetailsBinding.tvReadmoreMovieDetails.setVisibility(View.VISIBLE);
                    activityMovieDetailsBinding.tvReadmoreMovieDetails.setOnClickListener(view -> {
                        if (activityMovieDetailsBinding.tvReadmoreMovieDetails.getText().toString().equals("Read More")) {
                            activityMovieDetailsBinding.tvOverviewMovieDetails.setMaxLines(Integer.MAX_VALUE);
                            activityMovieDetailsBinding.tvOverviewMovieDetails.setEllipsize(null);
                            activityMovieDetailsBinding.tvReadmoreMovieDetails.setText(R.string.read_less);
                        } else {
                            activityMovieDetailsBinding.tvOverviewMovieDetails.setMaxLines(4);
                            activityMovieDetailsBinding.tvOverviewMovieDetails.setEllipsize(TextUtils.TruncateAt.END);
                            activityMovieDetailsBinding.tvReadmoreMovieDetails.setText(R.string.read_more);
                        }
                    });
                    activityMovieDetailsBinding.setRating(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Double.parseDouble(movieDetailsResponse.getVoteAverage())
                            )
                    );
                    if (movieDetailsResponse.getGenres() != null) {
                        activityMovieDetailsBinding.setGenre("Horror");
                    } else {
                        activityMovieDetailsBinding.setGenre("N/A");
                    }
                    activityMovieDetailsBinding.setType(movieDetailsResponse.getStatus());
                    activityMovieDetailsBinding.viewFadingDivider1.setVisibility(View.VISIBLE);
                    activityMovieDetailsBinding.llMisc.setVisibility(View.VISIBLE);
                    activityMovieDetailsBinding.viewFadingDivider2.setVisibility(View.VISIBLE);
                    loadBasicMovie();
                    activityMovieDetailsBinding.tvDateMovieDetails.setVisibility(View.VISIBLE);
                }
        );
    }

    private void loadBasicMovie() {
        activityMovieDetailsBinding.setName(getIntent().getStringExtra("name"));
        activityMovieDetailsBinding.setOriginalName(getIntent().getStringExtra("original_name"));
        activityMovieDetailsBinding.setLanguage(getIntent().getStringExtra("language"));
        activityMovieDetailsBinding.setDate(getIntent().getStringExtra("date"));
    }

}