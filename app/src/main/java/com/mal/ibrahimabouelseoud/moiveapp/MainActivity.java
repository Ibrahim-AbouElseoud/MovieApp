package com.mal.ibrahimabouelseoud.moiveapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity implements movieClickListener {
    MainActivityFragment fragMaster;
    MovieDetailsFragment fragDetail;
// using the key https://api.themoviedb.org/3/movie/550?api_key=202668e093a80f7cff8a7da31e8aafa8
    public final String  api_key="202668e093a80f7cff8a7da31e8aafa8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState==null) {
            if (getResources().getBoolean(R.bool.isTablet)) {
                fragMaster = new MainActivityFragment();
                fragDetail = new MovieDetailsFragment();
                getFragmentManager().beginTransaction().add(R.id.firstPanel, fragMaster).commit();
                getFragmentManager().beginTransaction().add(R.id.secondPanel, fragDetail).commit();
            }
            else{
                fragMaster = new MainActivityFragment();
                getFragmentManager().beginTransaction().add(R.id.firstPanel, fragMaster).commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void updateMovieDetailsView(String titleText,String releaseDateText,String plotText,double voteVal,String movieId,String posterUri) {
        fragDetail=(MovieDetailsFragment) getFragmentManager().findFragmentById(R.id.secondPanel);
        fragDetail.updateDetailsView(titleText,releaseDateText,plotText,voteVal,movieId,posterUri);
    }
}
