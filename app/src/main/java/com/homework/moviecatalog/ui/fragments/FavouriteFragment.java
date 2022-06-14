package com.homework.moviecatalog.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.homework.moviecatalog.databinding.FragmentFavouriteBinding;
import com.homework.moviecatalog.models.MovieModel;
import com.homework.moviecatalog.ui.activities.MovieDetailsActivity;
import com.homework.moviecatalog.ui.adapters.FavouriteAdapter;
import com.homework.moviecatalog.ui.listeners.FavouriteListener;
import com.homework.moviecatalog.utilities.TempDataHolder;
import com.homework.moviecatalog.viewmodels.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FavouriteFragment extends Fragment implements FavouriteListener {

    private FragmentFavouriteBinding binding;
    private FavouriteViewModel viewModel;
    private FavouriteAdapter adapter;
    private List<MovieModel> movieModels;

    public FavouriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);

        initialization();

        return binding.getRoot();
    }

    private void initialization() {
        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        movieModels = new ArrayList<>();
        loadFavourite();
    }

    private void loadFavourite() {
        binding.setIsLoading(true);
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.loadFavourite().subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieModels -> {
            binding.setIsLoading(false);
            if (this.movieModels.size() > 0) {
                this.movieModels.clear();
            }
            this.movieModels.addAll(movieModels);
            adapter = new FavouriteAdapter(this.movieModels, this);
            binding.rvListFavourite.setAdapter(adapter);
            binding.rvListFavourite.setVisibility(View.VISIBLE);
            compositeDisposable.dispose();
            if (this.movieModels.isEmpty()) {
                binding.tvEmptyFavourite.setVisibility(View.VISIBLE);
            }
        }));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (TempDataHolder.IS_FAVOURITE_UPDATED) {
            loadFavourite();
            TempDataHolder.IS_FAVOURITE_UPDATED = true;
        }
    }

    @Override
    public void onMovieClicked(MovieModel movieModel) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra("movie", movieModel);
        startActivity(intent);
    }

    @Override
    public void removeMovieFromFavourite(MovieModel movieModel, int position) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewModel.removeMovieFromFavourite(movieModel)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(() -> {
            movieModels.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, adapter.getItemCount());
            compositeDisposable.dispose();
            if (movieModels.isEmpty()) {
                binding.tvEmptyFavourite.setVisibility(View.VISIBLE);
            }
        }));
    }

}