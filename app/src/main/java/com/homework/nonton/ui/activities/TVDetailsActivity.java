package com.homework.nonton.ui.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.homework.nonton.BuildConfig;
import com.homework.nonton.R;
import com.homework.nonton.databinding.ActivityTvDetailsBinding;
import com.homework.nonton.models.TVModel;
import com.homework.nonton.viewmodels.TVDetailsViewModel;

import java.util.Locale;

public class TVDetailsActivity extends AppCompatActivity {

    private ActivityTvDetailsBinding binding;
    private TVDetailsViewModel viewModel;
    private TVModel tvModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tv_details);
        initialization();
    }

    private void initialization() {
        viewModel = new ViewModelProvider(this).get(TVDetailsViewModel.class);
        binding.ivBackTvDetails.setOnClickListener(view -> onBackPressed());
        tvModel = (TVModel) getIntent().getSerializableExtra("tv");
        getTVDetails();
    }

    private void getTVDetails() {
        binding.setIsLoading(true);
        String ID = String.valueOf(tvModel.getId());
        viewModel.getPopularTVDetails(ID, BuildConfig.API_KEY).observe(
                this, tvDetailsResponse -> {
                    binding.setIsLoading(false);
                    binding.setImageURL(tvDetailsResponse.getPosterPath());
                    binding.setImageBdURL(tvDetailsResponse.getBackdropPath());
                    binding.setOverview(tvDetailsResponse.getOverview());
                    binding.tvReadmoreTvDetails.setVisibility(View.VISIBLE);
                    binding.tvReadmoreTvDetails.setOnClickListener(view -> {
                        if (binding.tvReadmoreTvDetails.getText().toString().equals("Read More")) {
                            binding.tvOverviewTvDetails.setMaxLines(Integer.MAX_VALUE);
                            binding.tvOverviewTvDetails.setEllipsize(null);
                            binding.tvReadmoreTvDetails.setText(R.string.read_less);
                        } else {
                            binding.tvOverviewTvDetails.setMaxLines(4);
                            binding.tvOverviewTvDetails.setEllipsize(TextUtils.TruncateAt.END);
                            binding.tvReadmoreTvDetails.setText(R.string.read_more);
                        }
                    });
                    binding.setRating(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Double.parseDouble(tvDetailsResponse.getVoteAverage())
                            )
                    );
                    if (tvDetailsResponse.getGenres().isEmpty()){
                        binding.setGenre("N/A");
                    } else {
                        binding.setGenre(tvDetailsResponse.getGenres().get(0).getName());
                    }
                    binding.setType(tvDetailsResponse.getType());
                    binding.viewFadingDivider1.setVisibility(View.VISIBLE);
                    binding.llMisc.setVisibility(View.VISIBLE);
                    binding.viewFadingDivider2.setVisibility(View.VISIBLE);
                    loadBasicTVShows();
                    binding.tvDateTvDetails.setVisibility(View.VISIBLE);
                }
        );
    }

    private void loadBasicTVShows() {
        binding.setName(tvModel.getName());
        binding.setOriginalName(tvModel.getOriginalName());
        binding.setLanguage(tvModel.getOriginalLanguage());
        binding.setDate(tvModel.getFirstAirDate());
    }

}