package com.homework.nonton.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.R;
import com.homework.nonton.databinding.MovieItemListMoreBinding;
import com.homework.nonton.models.MovieModel;
import com.homework.nonton.ui.listeners.FavouriteListener;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.MovieViewHolder> {

    private List<MovieModel> movieModels;
    private LayoutInflater layoutInflater;
    private FavouriteListener favouriteListener;

    public FavouriteAdapter(List<MovieModel> movieModels, FavouriteListener favouriteListener) {
        this.movieModels = movieModels;
        this.favouriteListener = favouriteListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        MovieItemListMoreBinding movieBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item_list_more, parent, false);
        return new FavouriteAdapter.MovieViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindMoviePopular(movieModels.get(position));
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemListMoreBinding movieItemListBinding;

        public MovieViewHolder(MovieItemListMoreBinding movieItemListBinding) {
            super(movieItemListBinding.getRoot());
            this.movieItemListBinding = movieItemListBinding;
        }

        public void bindMoviePopular(MovieModel movieModel) {
            movieItemListBinding.setMovieModel(movieModel);
            movieItemListBinding.executePendingBindings();
            movieItemListBinding.getRoot().setOnClickListener(view -> favouriteListener.onMovieClicked(movieModel));
            movieItemListBinding.ivRemove.setOnClickListener(view -> favouriteListener.removeMovieFromFavourite(movieModel, getAdapterPosition()));
            movieItemListBinding.ivRemove.setVisibility(View.VISIBLE);
        }

    }

}
