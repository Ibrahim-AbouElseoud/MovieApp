package com.mal.ibrahimabouelseoud.moiveapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ibrahim Abou Elseoud on 16-Sep-16.
 */
public class TrailerAdapter extends ArrayAdapter<Trailer>{
    private Context context;
    private ArrayList<Trailer> trailers;
    Trailer trailer;
    public TrailerAdapter(Context mContext,ArrayList<Trailer> data){
        super(mContext,0,data);
        this.context=mContext;
        trailers=data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        if(convertView ==null){ // if new for recycling
            button= new Button(context);
            button.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        }
        else{
            button=(Button) convertView;
        }
         trailer= trailers.get(position);
        button.setText(trailer.title);
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent videoStart = new Intent(Intent.ACTION_VIEW);
//                videoStart.setDataAndType(Uri.parse(trailer.youtubeURL), "video/*");
//                getContext().startActivity(videoStart);

                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.youtubeURL));
                context.startActivity(i);
            }
        }
        );
        return button;
    }
}
