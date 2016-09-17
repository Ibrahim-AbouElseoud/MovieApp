package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ibrahim Abou Elseoud on 16-Sep-16.
 */
public class ReviewAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Review> reviews;
    Review review;
    public ReviewAdapter(Context mContext,ArrayList<Review> data){
        super(mContext,0,data);
        this.context=mContext;
        reviews=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView author;
        TextView reviewBody;
        if(convertView ==null){ // if new for recycling
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_view, parent, false);
        }
        author=(TextView) convertView.findViewById(R.id.authorTextView);
        reviewBody=(TextView) convertView.findViewById(R.id.reviewBodyTextView);
        review= reviews.get(position);
        author.setText("Review written by "+review.author);
        reviewBody.setText(review.content);

        return convertView;
    }
}
