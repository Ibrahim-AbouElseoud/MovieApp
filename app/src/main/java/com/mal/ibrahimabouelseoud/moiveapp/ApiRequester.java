package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Ibrahim Abou Elseoud on 13-Aug-16.
 */
public class ApiRequester { //update UI here


    private String api_key = "202668e093a80f7cff8a7da31e8aafa8";
    private String popularUrl = "http://api.themoviedb.org/3/movie/popular?api_key=202668e093a80f7cff8a7da31e8aafa8";
    private String topUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=202668e093a80f7cff8a7da31e8aafa8";
    //    private String url ="https://api.themoviedb.org/3/movie/550?api_key=202668e093a80f7cff8a7da31e8aafa8";
    private String imgUrl = "http://image.tmdb.org/t/p/w185"; //concatinate movie id after
    private RequestQueue queue;
    private JSONArray response;
    private ArrayList<Movie> moviesArray;
    private Context context;
    private UpdatableFragment myFragment;


    public ArrayList<Movie> getMoviesArray() {
        return moviesArray;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getPopularUrl() {
        return popularUrl;
    }

    public String getTopUrl() {
        return topUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public ApiRequester(Context context, MainActivityFragment currentFragment) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
//        moviesArray= new ArrayList<Movie>();
        myFragment = currentFragment;
    }

    public void getPopular() {
        moviesArray = new ArrayList<Movie>();
        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, popularUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movie = results.getJSONObject(i);

                                String title = movie.getString("title").toString();
                                String releaseDate = movie.getString("release_date").toString();
                                double voteAvg = Double.parseDouble(movie.getString("vote_average").toString());
                                String plot = movie.getString("overview").toString();
                                String posterUri = movie.getString("poster_path");
//                                Log.i("ay 7agaaaa", "onResponse: ");

                                moviesArray.add(new Movie(title, releaseDate, voteAvg, plot, posterUri));
                            }
//                            ImageAdapter a = new ImageAdapter(context, moviesArray);
                            myFragment.updateView(moviesArray);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
                Log.i("errorrrr", "onErrorResponse: ");
                error.printStackTrace();
            }
        });
// Add the request to the RequestQueue.
        queue.add(getRequest);
    }

    public void getTopRated() {
        moviesArray = new ArrayList<Movie>();
        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, topUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movie = results.getJSONObject(i);

                                String title = movie.getString("title").toString();
                                String releaseDate = movie.getString("release_date").toString();
                                double voteAvg = Double.parseDouble(movie.getString("vote_average").toString());
                                String plot = movie.getString("overview").toString();
                                String posterUri = movie.getString("poster_path");

                                moviesArray.add(new Movie(title, releaseDate, voteAvg, plot, posterUri));
                            }
//                            ImageAdapter a = new ImageAdapter(context, moviesArray);
                            myFragment.updateView(moviesArray);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
                error.printStackTrace();
            }

        });
// Add the request to the RequestQueue.
        queue.add(getRequest);
    }

}
