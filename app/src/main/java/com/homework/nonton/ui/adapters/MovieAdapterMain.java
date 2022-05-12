package com.homework.nonton.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.nonton.R;
import com.homework.nonton.databinding.MovieItemListMainBinding;
import com.homework.nonton.ui.listeners.MovieListener;
import com.homework.nonton.models.MovieModel;

import java.util.List;

public class MovieAdapterMain extends RecyclerView.Adapter<MovieAdapterMain.MovieViewHolder> {

    private Context context;
    private List<MovieModel> movieModels;
    private LayoutInflater layoutInflater;
    private MovieListener movieListener;

    public MovieAdapterMain(List<MovieModel> movieModels, MovieListener movieListener) {
        this.movieModels = movieModels;
        this.movieListener = movieListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        MovieItemListMainBinding movieBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item_list_main, parent, false);
        return new MovieAdapterMain.MovieViewHolder(movieBinding);
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

        private MovieItemListMainBinding movieItemListBinding;

        public MovieViewHolder(MovieItemListMainBinding movieItemListBinding) {
            super(movieItemListBinding.getRoot());
            this.movieItemListBinding = movieItemListBinding;
        }

        public void bindMoviePopular(MovieModel movieModel) {
            movieItemListBinding.setMovieModel(movieModel);
            movieItemListBinding.executePendingBindings();
            movieItemListBinding.getRoot().setOnClickListener(view -> movieListener.onMovieClicked(movieModel));
        }

    }

}
