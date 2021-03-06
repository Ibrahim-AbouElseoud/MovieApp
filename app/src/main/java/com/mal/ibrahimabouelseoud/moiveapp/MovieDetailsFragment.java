package com.mal.ibrahimabouelseoud.moiveapp;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Fragment containing the Details of the Movie
 */
public class MovieDetailsFragment extends Fragment implements UpdatableFragment{
    TextView title;
    TextView releaseDate;
    TextView vote;
    TextView overView;
    ImageView imageBox;
    View lineBreak;
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
    int favIndex;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

     View rootView= inflater.inflate(R.layout.fragment_movie_details, container, false);
        title= (TextView) rootView.findViewById(R.id.textTitle);
         releaseDate= (TextView) rootView.findViewById(R.id.textReleaseDate);
         vote= (TextView) rootView.findViewById(R.id.textVote);
         overView = (TextView) rootView.findViewById(R.id.textOverView);
         imageBox =  (ImageView) rootView.findViewById(R.id.imageViewPicture);
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
        // if there is saved data , then load current user's movie data and display (updateDetailView)
        if(savedInstanceState !=null){
            updateDetailsView(savedInstanceState.getString("title"),
                    savedInstanceState.getString("releaseDate"),
                    savedInstanceState.getString("plot"),
                    savedInstanceState.getDouble("vote"),
                    savedInstanceState.getString("id"),
                    savedInstanceState.getString("posterUri"));
        }
        //otherwise it's first time to open and no data yet for detail so if tablet show nothing but message to click a movie , if phone then just load data from the intent
        else {
            if (getResources().getBoolean(R.bool.isTablet)) {

                title.setText("Please click on a poster from the left to get it's Details here");
                releaseDate.setText("");
                vote.setText("");
                overView.setText("");
                lineBreak.setVisibility(View.INVISIBLE);
                imageBox.setVisibility(View.INVISIBLE);
            } else {
                updateDetailsView();
            }
        }




    }
//Save current user movie data (in case of screen orientation change for example)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", titleText);
        outState.putString("releaseDate", releaseDateText);
        outState.putDouble("vote", voteVal);
        outState.putString("plot", plotText);
        outState.putString("posterUri", posterUri);
        outState.putString("id", movieId);
    }

    @Override
    public void updateMasterView(ArrayList<Movie> moviesArray) {

    }

// load trailer's (called from API Requester since it has the data)
    @Override
    public void updateDetailTrailer(ArrayList<Trailer> trailerArray) {
        trailerListView.setAdapter(new TrailerAdapter(getActivity(),trailerArray));
    }

//load review's (called from API Requester since it has the data)
    @Override
    public void updateDetailReview(ArrayList<Review> reviewArray) {
        reviewListView.setAdapter(new ReviewAdapter(getActivity(),reviewArray));
    }
    //Check if the movie is in favorites if it is then it will give it's index for easy direct removal if not found then it will return -1
    public int isInFavoritesIndex(String title){
        ArrayList<Movie> favMovies = MainActivityFragment.favMovies;
        for(int i=0;i<favMovies.size();i++) {
            if (favMovies.get(i).title.equals(title)) {
                return i;
            }
        }
        return -1; //meaning not found
    }
    //update the details view for mobile (used for mobile only since it relies on intents)
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


        posterUri= i.getStringExtra("posterUri");
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+posterUri).into(imageBox);
        favBtn.setVisibility(View.VISIBLE);
        updateFavButton();

    }

    //update the details view used mainly for tablet and to display the active movie for mobile (used generally when you have the data)
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


        this.posterUri= posterUri;
        Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w185"+posterUri).into(imageBox);
        favBtn.setVisibility(View.VISIBLE);
        updateFavButton();
    }
    //updates the Favorite button to display "Remove from favorites" in case of movie in favorites or "Add to Favorites" incase not in favorites and supplies with the correct click listener)
    public void updateFavButton(){
        favIndex=isInFavoritesIndex(titleText);
        if(favIndex!=-1){
            favBtn.setText("Remove from Favorites");
            favBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Movie> favMovies = MainActivityFragment.favMovies;
                    favMovies.remove(favIndex);
                    tinydb.putListMovie("favoriteMovies",favMovies);
                    Toast.makeText(getActivity(), "removed " + titleText+ " from favorites!",
                            Toast.LENGTH_SHORT).show();
                    favBtn.setVisibility(View.INVISIBLE);
                }
            });
        }
        else {
            favBtn.setText("Add to Favorites");
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub

                    ArrayList<Movie> favMovies = MainActivityFragment.favMovies;

                    Movie favMovie = new Movie(titleText, releaseDateText, voteVal, plotText, posterUri, movieId);
                    favMovies.add(favMovie);
                    tinydb.putListMovie("favoriteMovies", favMovies);
                    Toast.makeText(getActivity(), "added " + titleText + " to favorites!",
                            Toast.LENGTH_SHORT).show();
                    favBtn.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

}
