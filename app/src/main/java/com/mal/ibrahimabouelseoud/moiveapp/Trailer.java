package com.mal.ibrahimabouelseoud.moiveapp;

/**
 * Created by Ibrahim Abou Elseoud on 16-Sep-16.
 */
public class Trailer {
    String title;
    String key;
    String youtubeURL;
    public Trailer(String title,
                 String key){
        this.title=title;
        this.key=key;
    }
    public Trailer(String title,
                   String key, String youtubeURL){
        this.title=title;
        this.key=key;
        this.youtubeURL=youtubeURL;
    }
    public void setYoutubeURL(){
        youtubeURL="https://www.youtube.com/watch?v="+key;
    }
}
