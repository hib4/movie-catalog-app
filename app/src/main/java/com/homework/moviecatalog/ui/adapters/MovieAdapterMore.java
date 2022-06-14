package com.homework.moviecatalog.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.homework.moviecatalog.R;
import com.homework.moviecatalog.databinding.MovieItemListMoreBinding;
import com.homework.moviecatalog.models.MovieModel;
import com.homework.moviecatalog.ui.listeners.MovieListener;

import java.util.List;

public class MovieAdapterMore extends RecyclerView.Adapter<MovieAdapterMore.MovieViewHolder> {

    private List<MovieModel> movieModels;
    private LayoutInflater layoutInflater;
    private MovieListener movieListener;

    public MovieAdapterMore(List<MovieModel> movieModels, MovieListener movieListener) {
        this.movieModels = movieModels;
        this.movieListener = movieListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        MovieItemListMoreBinding movieBinding = DataBindingUtil.inflate(layoutInflater, R.layout.movie_item_list_more, parent, false);
        return new MovieAdapterMore.MovieViewHolder(movieBinding);
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
            movieItemListBinding.getRoot().setOnClickListener(view -> movieListener.onMovieClicked(movieModel));
        }

    }

}
