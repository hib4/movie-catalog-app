package com.homework.nonton.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.homework.nonton.BuildConfig;
import com.homework.nonton.R;
import com.homework.nonton.databinding.ActivityMovieDetailsBinding;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.utilities.TempDataHolder;
import com.homework.nonton.viewmodels.MovieDetailsViewModel;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetailsActivity extends AppCompatActivity {

    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;
    private MovieModel movieModel;
    private Boolean isMovieAvailableInFavourite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details);
        initialization();
    }

    private void initialization() {
        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);
        binding.ivBackMovieDetails.setOnClickListener(view -> onBackPressed());
        movieModel = (MovieModel) getIntent().getSerializableExtra("movie");
        checkMovieInFavourite();
        getMovieDetails();
    }

    private void checkMovieInFavourite() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.getMovieFromFavourite(String.valueOf(movieModel.getId()))
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieModel1 -> {
            isMovieAvailableInFavourite = true;
            binding.ivAddFavourite.setImageResource(R.drawable.ic_added);
            compositeDisposable.dispose();
        }));
    }

    private void getMovieDetails() {
        binding.setIsLoading(true);
        String ID = String.valueOf(movieModel.getId());
        viewModel.getPopularMovieDetails(ID, BuildConfig.API_KEY).observe(
                this, movieDetailsResponse -> {
                    binding.setIsLoading(false);
                    binding.setImageURL(movieDetailsResponse.getPosterPath());
                    binding.setImageBdURL(movieDetailsResponse.getBackdropPath());
                    binding.setOverview(movieDetailsResponse.getOverview());
                    binding.tvReadmoreMovieDetails.setVisibility(View.VISIBLE);
                    binding.tvReadmoreMovieDetails.setOnClickListener(view -> {
                        if (binding.tvReadmoreMovieDetails.getText().toString().equals("Read More")) {
                            binding.tvOverviewMovieDetails.setMaxLines(Integer.MAX_VALUE);
                            binding.tvOverviewMovieDetails.setEllipsize(null);
                            binding.tvReadmoreMovieDetails.setText(R.string.read_less);
                        } else {
                            binding.tvOverviewMovieDetails.setMaxLines(4);
                            binding.tvOverviewMovieDetails.setEllipsize(TextUtils.TruncateAt.END);
                            binding.tvReadmoreMovieDetails.setText(R.string.read_more);
                        }
                    });
                    binding.setRating(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Double.parseDouble(movieDetailsResponse.getVoteAverage())
                            )
                    );
                    if (movieDetailsResponse.getGenres().isEmpty()) {
                        binding.setGenre("N/A");
                    } else {
                        binding.setGenre(movieDetailsResponse.getGenres().get(0).getName());
                    }
                    binding.setType(movieDetailsResponse.getStatus());
                    binding.viewFadingDivider1.setVisibility(View.VISIBLE);
                    binding.llMisc.setVisibility(View.VISIBLE);
                    binding.viewFadingDivider2.setVisibility(View.VISIBLE);
                    binding.tvDateMovieDetails.setVisibility(View.VISIBLE);
                    binding.ivAddFavourite.setOnClickListener(view -> {
                        CompositeDisposable compositeDisposable = new CompositeDisposable();
                        if (isMovieAvailableInFavourite) {
                            compositeDisposable.add(viewModel.removeMovieFromFavourite(movieModel)
                            .subscribeOn(Schedulers.computation())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(() -> {
                                isMovieAvailableInFavourite = false;
                                TempDataHolder.IS_FAVOURITE_UPDATED = true;
                                binding.ivAddFavourite.setImageResource(R.drawable.ic_favourite);
                                Toast.makeText(getApplicationContext(), "Removed from favourite", Toast.LENGTH_SHORT).show();
                                compositeDisposable.dispose();
                            }));
                        } else {
                            compositeDisposable.add(viewModel.addToFavourite(movieModel)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        TempDataHolder.IS_FAVOURITE_UPDATED = true;
                                        binding.ivAddFavourite.setImageResource(R.drawable.ic_added);
                                        Toast.makeText(getApplicationContext(), "Added to Favourite", Toast.LENGTH_SHORT).show();
                                        compositeDisposable.dispose();
                                    })
                            );
                        }
                    });
                    binding.ivAddFavourite.setVisibility(View.VISIBLE);
                    loadBasicMovie();
                }
        );
    }

    private void loadBasicMovie() {
        binding.setName(movieModel.getTitle());
        binding.setOriginalName(movieModel.getOriginalTitle());
        binding.setLanguage(movieModel.getOriginalLanguage());
        binding.setDate(movieModel.getReleaseDate());
    }

}