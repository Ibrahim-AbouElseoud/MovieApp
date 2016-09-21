package com.mal.ibrahimabouelseoud.moiveapp;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements UpdatableFragment {
    ApiRequester requester;
    GridView gridview;
    ArrayList<Movie> movies;
    static ArrayList<Movie> favMovies;//use to see if in favorites or not
    TinyDB tinydb;

     int activeState;//0 means in popular , 1 means in top rated , 2 means in favorites

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false); //draws into root or creates it
        gridview = (GridView) root.findViewById(R.id.gridview);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getActivity(), "" + movies.get(position).title,
                        Toast.LENGTH_SHORT).show();

                Movie movie=movies.get(position);
                String title = movie.title;
                String releaseDate = movie.releaseDate;
                double voteAvg = movie.voteAvg;
                String plot = movie.plot;
                String posterUri=movie.posterUri;
                String idTxt=movie.id;
                if(!getResources().getBoolean(R.bool.isTablet)) {
                    Intent intent = new Intent(getActivity(), MovieDetailsActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("releaseDate", releaseDate);
                    intent.putExtra("vote", voteAvg);
                    intent.putExtra("plot", plot);
                    intent.putExtra("posterUri", posterUri);
                    intent.putExtra("id", idTxt);
                    startActivity(intent);
                }
                else{
                   movieClickListener parentActivity= (movieClickListener)getActivity();
                    parentActivity.updateMovieDetailsView(title,releaseDate,plot,voteAvg,idTxt,posterUri);
                }
            }
        });

        tinydb= new TinyDB(getActivity());
        requester = new ApiRequester(getActivity(),MainActivityFragment.this);

        try {
            favMovies = tinydb.getListMovie("favoriteMovies", Movie.class);
        }
        catch(Exception e){ //then does not exist yet
            favMovies=new ArrayList<Movie>();
            tinydb.putListMovie("favoriteMovies",favMovies);
        }
//        final Switch switcher = (Switch) root.findViewById(R.id.switch1);
//        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // do something, the isChecked will be
//                if(isChecked){
//                    switcher.setText("Top Rated");
//                    requester.getTopRated();
//
//                }
//                else {
//                    switcher.setText("Most Popular");
//                    requester.getPopular();
//
//
//
//                }
//                // true if the switch is in the On position
//            }
//        });
        if(savedInstanceState !=null){
            activeState=savedInstanceState.getInt("activeState");
            Log.i("active view", "onCreateView: "+ activeState);
            switch(activeState){
                case 2: displayFavorites();break;
                case 1:requester.getTopRated();break;
                case 0:requester.getPopular();break;
            }
        }
        else requester.getPopular();

        return root;

    }


    public void updateMasterView(ArrayList<Movie> moviesArray) {
        movies=moviesArray;
        gridview.setAdapter(new ImageAdapter(getActivity(),moviesArray));


    }

    @Override
    public void updateDetailTrailer(ArrayList<Trailer> trailerArray) {

    }

    @Override
    public void updateDetailReview(ArrayList<Review> reviewArray) {

    }

    public void displayFavorites(){
        if(favMovies.size()>0) {
//            favMovies=tinydb.getListMovie("favoriteMovies",Movie.class); //update our current favorite array
            activeState=2;
            updateMasterView(favMovies);
        }
        else Toast.makeText(getActivity(), "No favorites saved in app yet!", Toast.LENGTH_LONG).show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorites) {
            displayFavorites();
            return true;
        }
        switch (id){
            case R.id.action_favorites:{ displayFavorites();  }break;
            case R.id.action_top:{activeState=1;requester.getTopRated();}break;
            case R.id.action_popular:{activeState=0;requester.getPopular();}break;
        }
//        Log.i("active view", "onCreateViewMenu: "+ activeState);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("activeState",activeState);
    }
}
