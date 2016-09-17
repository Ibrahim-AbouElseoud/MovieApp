package com.mal.ibrahimabouelseoud.moiveapp;

import java.util.ArrayList;

/**
 * Created by Ibrahim Abou Elseoud on 20-Aug-16.
 */
public interface UpdatableFragment {
    public void updateMasterView(ArrayList<Movie> moviesArray);
    public void updateDetailTrailer(ArrayList<Trailer> trailerArray);
    public void updateDetailReview(ArrayList<Review> reviewArray);

}
