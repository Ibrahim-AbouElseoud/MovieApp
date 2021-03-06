package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Context;
import android.widget.Toast;

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
 *
 * API Requester is responsible for doing all API requests (except images) and when it finishes populating it's array it calls on a method from the interface of the fragment
 */
public class ApiRequester { //update UI here


    private String api_key = "202668e093a80f7cff8a7da31e8aafa8";
    private String popularUrl = "http://api.themoviedb.org/3/movie/popular?api_key=202668e093a80f7cff8a7da31e8aafa8";
    private String topUrl = "http://api.themoviedb.org/3/movie/top_rated?api_key=202668e093a80f7cff8a7da31e8aafa8";
    private String imgUrl = "http://image.tmdb.org/t/p/w185"; //concatinate movie id after
    private RequestQueue queue;
    private JSONArray response;
    private ArrayList<Movie> moviesArray;
    private Context context;
    private UpdatableFragment myFragment;
    private ArrayList<Trailer> trailers;
    private ArrayList<Review> reviews;
    private MovieDetailsFragment movieDetailsFragment;


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

    public ApiRequester(Context context, UpdatableFragment currentFragment) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        myFragment = currentFragment;
    }
// gets the popular movies then forwards the data to the fragment's interface (specifically the MainActivityFragment) when done.
    public void getPopular() {
        moviesArray = new ArrayList<Movie>();
        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, popularUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movie = results.getJSONObject(i);

                                String title = movie.getString("title").toString();
                                String releaseDate = movie.getString("release_date").toString();
                                double voteAvg = Double.parseDouble(movie.getString("vote_average").toString());
                                String plot = movie.getString("overview").toString();
                                String posterUri = movie.getString("poster_path");
                                String id = movie.getString("id");

                                moviesArray.add(new Movie(title, releaseDate, voteAvg, plot, posterUri,id));
                            }
                            myFragment.updateMasterView(moviesArray);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Toast.makeText(context, "Network error , please check your network !",
                        Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(getRequest);
    }

// gets the Top rated movies then forwards the data to the fragment's interface (specifically the MainActivityFragment) when done.
    public void getTopRated() {
        moviesArray = new ArrayList<Movie>();
        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, topUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject movie = results.getJSONObject(i);

                                String title = movie.getString("title").toString();
                                String releaseDate = movie.getString("release_date").toString();
                                double voteAvg = Double.parseDouble(movie.getString("vote_average").toString());
                                String plot = movie.getString("overview").toString();
                                String posterUri = movie.getString("poster_path");
                                String id = movie.getString("id");

                                moviesArray.add(new Movie(title, releaseDate, voteAvg, plot, posterUri,id));
                            }
                            myFragment.updateMasterView(moviesArray);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Network error , please check your network !",
                        Toast.LENGTH_LONG).show();
            }

        });
// Add the request to the RequestQueue.
        queue.add(getRequest);
    }

    // gets the Review's author and content then forwards the data to the fragment's interface(specifically the MovieDetailsFragment) when done.
    public void getReviews(String movieId) {
        reviews = new ArrayList<Review>();
        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/"+movieId+"/reviews?api_key=202668e093a80f7cff8a7da31e8aafa8", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject review = results.getJSONObject(i);

                                String author = review.getString("author").toString();
                                String content = review.getString("content").toString();

                                reviews.add(new Review(author, content));
                            }
                            myFragment.updateDetailReview(reviews);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Network error while getting reviews , please check your network !",
                        Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(getRequest);
    }

    // gets the trailer then forwards the data to the fragment's interface (specifically the MovieDetailsFragment) when done.
    public void getTrailers(String movieId) {
        trailers = new ArrayList<Trailer>();
        // Request a string response from the provided URL.
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, "http://api.themoviedb.org/3/movie/"+movieId+"/videos?api_key=202668e093a80f7cff8a7da31e8aafa8", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray results = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++) {
                                JSONObject trailer = results.getJSONObject(i);

                                String name = trailer.getString("name").toString();
                                String key = trailer.getString("key").toString();
                                Trailer t=new Trailer(name,key);
                                t.setYoutubeURL();
                                trailers.add(t);
                            }

                            myFragment.updateDetailTrailer(trailers);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(context, "Network error while getting trailer , please check your network !",
                        Toast.LENGTH_LONG).show();
            }
        });
// Add the request to the RequestQueue.
        queue.add(getRequest);
    }
}
