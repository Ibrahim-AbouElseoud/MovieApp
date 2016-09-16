package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    Button favBtn;
    TinyDB tinydb;

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
        favBtn=(Button) rootView.findViewById(R.id.favoriteButton);
        tinydb = new TinyDB(getContext());

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Intent i = getActivity().getIntent();
       final String titleText= i.getStringExtra("title");
        title.setText(titleText);
        final String releaseDateText= i.getStringExtra("releaseDate");
        releaseDate.setText("Released on "+releaseDateText);
        final String plotText= i.getStringExtra("plot");
        overView.setText(plotText);
        final double voteVal=i.getDoubleExtra("vote",0.0);
        vote.setText("Vote Average "+voteVal);
//        ratingBar.setRating((float)voteVal);
//        ratingBar.setIsIndicator(true);

        final String posterUri= i.getStringExtra("posterUri");
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+posterUri).into(imageBox);
        favBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                // Idea 1 save each movie individually in tinyDB
//                int maxIndex;
//                try {
//                    maxIndex= tinydb.getInt("maxIndex");
//                }
//                catch(Exception e){ //if first time and maxIndex not defined then max is -1
//                    tinydb.putInt("maxIndex",-1);
//                    maxIndex=-1;
//                }
//                Movie favMovie=new Movie(titleText,releaseDateText, voteVal,plotText,posterUri);
//                maxIndex++;
//                tinydb.putObject("fav"+maxIndex,favMovie); //key in form of fav0 for first movie

                //Idea 2 save a movie array and edit it each time using tinyDB

                ArrayList<Movie> favMovies;

                try{
                    favMovies=tinydb.getListMovie("favoriteMovies",Movie.class);
                }catch(Exception e){ //if first time and favMovies not defined then do new one and save movie
                    favMovies=new ArrayList<Movie>();
                     Movie favMovie=new Movie(titleText,releaseDateText, voteVal,plotText,posterUri);
                    favMovies.add(favMovie);
                    tinydb.putListMovie("favoriteMovies",favMovies);
                }
                Toast.makeText(getContext(), "added " + titleText+ " to favorites!",
                        Toast.LENGTH_SHORT).show();


            }
        });

    }
}
