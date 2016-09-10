package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {
    TextView title;
    TextView releaseDate;
    TextView vote;
    TextView overView;
    ImageView imageBox;
//    RatingBar ratingBar;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     View rootView= inflater.inflate(R.layout.fragment_movie_details, container, false);
        title= (TextView) rootView.findViewById(R.id.textTitle);
         releaseDate= (TextView) rootView.findViewById(R.id.textReleaseDate);
         vote= (TextView) rootView.findViewById(R.id.textVote);
         overView = (TextView) rootView.findViewById(R.id.textOverView);
         imageBox =  (ImageView) rootView.findViewById(R.id.imageViewPicture);
//        ratingBar= (RatingBar) rootView.findViewById(R.id.ratingBar);

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent i = getActivity().getIntent();
        String titleText= i.getStringExtra("title");
        title.setText(titleText);
        String releaseDateText= i.getStringExtra("releaseDate");
        releaseDate.setText("Released on "+releaseDateText);
        String plotText= i.getStringExtra("plot");
        overView.setText(plotText);
        double voteVal=i.getDoubleExtra("vote",0.0);
        vote.setText("Vote Average "+voteVal);
//        ratingBar.setRating((float)voteVal);
//        ratingBar.setIsIndicator(true);

        String posterUri= i.getStringExtra("posterUri");
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+posterUri).into(imageBox);



    }
}
