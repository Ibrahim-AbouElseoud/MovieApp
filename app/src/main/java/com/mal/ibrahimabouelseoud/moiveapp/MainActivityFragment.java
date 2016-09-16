package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
    ArrayList<Movie> favoriteMovies;
    public MainActivityFragment() {
    }

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
                Toast.makeText(getContext(), "" + movies.get(position).title,
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
                Movie movie=movies.get(position);
                String title = movie.title;
                String releaseDate = movie.releaseDate;
                double voteAvg = movie.voteAvg;
                String plot = movie.plot;
                String posterUri=movie.posterUri;
                intent.putExtra("title",title);
                intent.putExtra("releaseDate",releaseDate);
                intent.putExtra("vote",voteAvg);
                intent.putExtra("plot",plot);
                intent.putExtra("posterUri",posterUri);


                startActivity(intent);
            }
        });


         requester = new ApiRequester(getContext(),MainActivityFragment.this);
        requester.getPopular();
        ArrayList<Movie> result = requester.getMoviesArray();
        System.out.println(result.size());
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

        return root;

    }


    public void updateView(ArrayList<Movie> moviesArray) {
        movies=moviesArray;
        gridview.setAdapter(new ImageAdapter(getContext(),moviesArray));


    }
    public void displayFavorites(){
        TinyDB tinydb = new TinyDB(getContext());
        ArrayList<Movie> favMovies;
        try{
            favMovies=tinydb.getListMovie("favoriteMovies",Movie.class);
//            Toast.makeText(getContext(), favMovies.size()+"test",
//                    Toast.LENGTH_LONG).show();
            if(favMovies.size()>0)
            updateView(favMovies);
            else Toast.makeText(getContext(), "No favorites saved in app yet!", Toast.LENGTH_LONG).show();
        }catch(Exception e){ //no favorites added yet
            Toast.makeText(getContext(), "No favorites saved in app yet!",
                    Toast.LENGTH_LONG).show();
        }
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
            case R.id.action_favorites: displayFavorites();break;
            case R.id.action_top:requester.getTopRated();break;
            case R.id.action_popular:requester.getPopular();break;
        }
        return super.onOptionsItemSelected(item);
    }

}
