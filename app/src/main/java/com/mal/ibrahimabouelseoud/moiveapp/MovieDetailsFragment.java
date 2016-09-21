package com.mal.ibrahimabouelseoud.moiveapp;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment implements UpdatableFragment{
    TextView title;
    TextView releaseDate;
    TextView vote;
    TextView overView;
    ImageView imageBox;
    View lineBreak;
//    RatingBar ratingBar;
    Button favBtn;
    TinyDB tinydb;
    ListView trailerListView;
    ListView reviewListView;
    ApiRequester requester;
    /// data
    String titleText;
    String releaseDateText;
    String plotText;
    double voteVal;
    String movieId;
    String posterUri;



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
        lineBreak=(View) rootView.findViewById(R.id.SplitLine_hor1);
        tinydb = new TinyDB(getActivity());

        trailerListView =(ListView) rootView.findViewById(R.id.trailerListView);
        reviewListView =(ListView) rootView.findViewById(R.id.reviewListView);


        requester=new ApiRequester(getActivity(),MovieDetailsFragment.this);

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getResources().getBoolean(R.bool.isTablet)){

            title.setText("Please click on a poster from the left to get it's Details here");
            releaseDate.setText("");
             vote.setText("");
             overView.setText("");
            lineBreak.setVisibility(View.INVISIBLE);
            imageBox.setVisibility(View.INVISIBLE);
        }
        else{
            updateDetailsView();
        }


        final int index=isInFavoritesIndex(titleText);
        if(index!=-1){
            favBtn.setText("Remove from Favorites");
            favBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Movie> favMovies = MainActivityFragment.favMovies;
                    favMovies.remove(index);
                    tinydb.putListMovie("favoriteMovies",favMovies);
                    Toast.makeText(getActivity(), "removed " + titleText+ " from favorites!",
                            Toast.LENGTH_SHORT).show();
                    favBtn.setVisibility(View.INVISIBLE);
                }
            });
        }
        else
        favBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ArrayList<Movie> favMovies = MainActivityFragment.favMovies;

                Movie favMovie=new Movie(titleText,releaseDateText, voteVal,plotText,posterUri,movieId);
                favMovies.add(favMovie);
                tinydb.putListMovie("favoriteMovies",favMovies);
                Toast.makeText(getActivity(), "added " + titleText+ " to favorites!",
                        Toast.LENGTH_SHORT).show();
                favBtn.setVisibility(View.INVISIBLE);
            }
        });

    }


    @Override
    public void updateMasterView(ArrayList<Movie> moviesArray) {

    }

    @Override
    public void updateDetailTrailer(ArrayList<Trailer> trailerArray) {
        trailerListView.setAdapter(new TrailerAdapter(getActivity(),trailerArray));
    }

    @Override
    public void updateDetailReview(ArrayList<Review> reviewArray) {
        reviewListView.setAdapter(new ReviewAdapter(getActivity(),reviewArray));
    }
    public int isInFavoritesIndex(String title){
        ArrayList<Movie> favMovies = MainActivityFragment.favMovies;
        for(int i=0;i<favMovies.size();i++) {
            if (favMovies.get(i).title.equals(title)) {
                return i;
            }
        }
        return -1; //meaning not found
    }
    public void updateDetailsView(){
        Intent i = getActivity().getIntent();
        titleText= i.getStringExtra("title");
        title.setText(titleText);
        releaseDateText= i.getStringExtra("releaseDate");
        releaseDate.setText("Released on "+releaseDateText);
        plotText= i.getStringExtra("plot");
        overView.setText(plotText);
        voteVal=i.getDoubleExtra("vote",0.0);
        vote.setText("Vote Average "+voteVal);

        movieId= i.getStringExtra("id");
        requester.getTrailers(movieId);
        requester.getReviews(movieId);
//        ratingBar.setRating((float)voteVal);
//        ratingBar.setIsIndicator(true);

        posterUri= i.getStringExtra("posterUri");
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+posterUri).into(imageBox);
    }
    public void updateDetailsView(String titleText,String releaseDateText,String plotText,double voteVal,String movieId,String posterUri){
        this.titleText=titleText;
        title.setText(titleText);
        this.releaseDateText= releaseDateText;
        releaseDate.setText("Released on "+releaseDateText);
        this.plotText=plotText;
        overView.setText(plotText);
        this.voteVal=voteVal;
        vote.setText("Vote Average "+voteVal);
        if(lineBreak.getVisibility()==View.INVISIBLE ){
            lineBreak.setVisibility(View.VISIBLE);
            imageBox.setVisibility(View.VISIBLE);
        }

        this.movieId= movieId;
        requester.getTrailers(movieId);
        requester.getReviews(movieId);
//        ratingBar.setRating((float)voteVal);
//        ratingBar.setIsIndicator(true);

        this.posterUri= posterUri;
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+posterUri).into(imageBox);
    }

}
