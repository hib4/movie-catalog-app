package com.homework.moviecatalog.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.homework.moviecatalog.R;
import com.homework.moviecatalog.databinding.SlideItemListBinding;
import com.homework.moviecatalog.models.MovieModel;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<MovieModel> movieModels;
    private LayoutInflater layoutInflater;
    private ViewPager2 viewPager2;

    public SliderAdapter(List<MovieModel> movieModels, ViewPager2 viewPager2) {
        this.movieModels = movieModels;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        SlideItemListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.slide_item_list, parent, false);
        return new SliderAdapter.SliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {
        holder.bindMoviePopular(movieModels.get(position));
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {

        private SlideItemListBinding binding;

        public SliderViewHolder(SlideItemListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindMoviePopular(MovieModel movieModel) {
            binding.setMovieModel(movieModel);
            binding.executePendingBindings();
        }
    }
}
