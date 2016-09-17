package com.mal.ibrahimabouelseoud.moiveapp;

import io.realm.RealmObject;

/**
 * Created by Ibrahim Abou Elseoud on 13-Aug-16.
 */
public class Movie {
    String title;
    String releaseDate;
    double voteAvg;
    String plot;
    String posterUri;
    String id;
    public Movie(String title,
            String releaseDate,
            double voteAvg,
            String plot,
            String posterUri,
            String id     ){
        this. title=title;
        this. releaseDate=releaseDate;
        this. voteAvg=voteAvg;
        this. plot=plot;
        this. posterUri=posterUri;
        this.id=id;

    }
}
