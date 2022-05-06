package com.homework.nonton.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.homework.nonton.R;
import com.homework.nonton.databinding.ActivityTvDetailsBinding;
import com.homework.nonton.models.TVDetailsModel;
import com.homework.nonton.viewmodels.TVDetailsViewModel;

import java.util.Locale;

public class TVDetailsActivity extends AppCompatActivity {

    private ActivityTvDetailsBinding activityTvDetailsBinding;
    private TVDetailsViewModel tvDetailsViewModel;
    private TVDetailsModel tvDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tv_details);
        doInitialization();
    }

    private void doInitialization() {
        tvDetailsViewModel = new ViewModelProvider(this).get(TVDetailsViewModel.class);
        activityTvDetailsBinding.ivBackTvDetails.setOnClickListener(view -> onBackPressed());
        getTVDetails();
    }

    private void getTVDetails() {
        activityTvDetailsBinding.setIsLoading(true);
        String ID = String.valueOf(getIntent().getIntExtra("id", -1));
        tvDetailsViewModel.getPopularTVDetails(ID, "6336e4208132f6206aa0b05d04b1fda7").observe(
                this, tvDetailsResponse -> {
                    activityTvDetailsBinding.setIsLoading(false);
                    activityTvDetailsBinding.setImageURL(tvDetailsResponse.getPosterPath());
                    activityTvDetailsBinding.setImageBdURL(tvDetailsResponse.getBackdropPath());
//                    activityTvDetailsBinding.setOverview(String.valueOf(HtmlCompat.fromHtml(tvDetailsResponse.getOverview(), HtmlCompat.FROM_HTML_MODE_LEGACY)));
                    activityTvDetailsBinding.setOverview(tvDetailsResponse.getOverview());
                    activityTvDetailsBinding.tvReadmoreTvDetails.setVisibility(View.VISIBLE);
                    activityTvDetailsBinding.tvReadmoreTvDetails.setOnClickListener(view -> {
                        if (activityTvDetailsBinding.tvReadmoreTvDetails.getText().toString().equals("Read More")) {
                            activityTvDetailsBinding.tvOverviewTvDetails.setMaxLines(Integer.MAX_VALUE);
                            activityTvDetailsBinding.tvOverviewTvDetails.setEllipsize(null);
                            activityTvDetailsBinding.tvReadmoreTvDetails.setText(R.string.read_less);
                        } else {
                            activityTvDetailsBinding.tvOverviewTvDetails.setMaxLines(4);
                            activityTvDetailsBinding.tvOverviewTvDetails.setEllipsize(TextUtils.TruncateAt.END);
                            activityTvDetailsBinding.tvReadmoreTvDetails.setText(R.string.read_more);
                        }
                    });
                    activityTvDetailsBinding.setRating(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Double.parseDouble(tvDetailsResponse.getVoteAverage())
                            )
                    );
                    if (tvDetailsResponse.getGenres() != null) {
                        activityTvDetailsBinding.setGenre("Action");
                    } else {
                        activityTvDetailsBinding.setGenre("N/A");
                    }
                    activityTvDetailsBinding.setType(tvDetailsResponse.getType());
                    activityTvDetailsBinding.viewFadingDivider1.setVisibility(View.VISIBLE);
                    activityTvDetailsBinding.llMisc.setVisibility(View.VISIBLE);
                    activityTvDetailsBinding.viewFadingDivider2.setVisibility(View.VISIBLE);
                    loadBasicTVShows();
                }
        );
    }

    private void loadBasicTVShows() {
        activityTvDetailsBinding.setName(getIntent().getStringExtra("name"));
        activityTvDetailsBinding.setOriginalName(getIntent().getStringExtra("original_name"));
        activityTvDetailsBinding.setLanguage(getIntent().getStringExtra("language"));
        activityTvDetailsBinding.setDate(getIntent().getStringExtra("date"));
    }

}