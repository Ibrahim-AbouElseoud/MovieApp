package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ibrahim Abou Elseoud on 13-Aug-16.
 */
public class ImageAdapter extends ArrayAdapter<Movie> {
    private Context context;
    private ArrayList<Movie> movies;

    public ImageAdapter(Context mContext,ArrayList<Movie> data){
        super(mContext,0,data);
        this.context=mContext;
        movies=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageBox;
        if(convertView ==null){ // if new for recycling
            imageBox= new ImageView(context);
            imageBox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }
        else{
            imageBox=(ImageView) convertView;
        }
        Movie movie= movies.get(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+movie.posterUri).into(imageBox);

        return imageBox;
    }
}