package com.mal.ibrahimabouelseoud.moiveapp;

/**
 * Created by Ibrahim Abou Elseoud on 13-Aug-16.
 */
public class Movie {
    String title;
    String releaseDate;
    double voteAvg;
    String plot;
    String posterUri;
    public Movie(String title,
            String releaseDate,
            double voteAvg,
            String plot,
            String posterUri){
        this. title=title;
        this. releaseDate=releaseDate;
        this. voteAvg=voteAvg;
        this. plot=plot;
        this. posterUri=posterUri;

    }
}
