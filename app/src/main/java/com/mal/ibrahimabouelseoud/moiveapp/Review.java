package com.mal.ibrahimabouelseoud.moiveapp;

/**
 * Created by Ibrahim Abou Elseoud on 16-Sep-16.
 */
public class Review {
    String author;
    String content;
    String fullReviewURL;

    public Review(String author,String content, String fullReviewURL){
        this.author=author;
        this.content=content;
        this.fullReviewURL=fullReviewURL;
    }
    public Review(String author,String content){
        this.author=author;
        this.content=content;
    }
}
