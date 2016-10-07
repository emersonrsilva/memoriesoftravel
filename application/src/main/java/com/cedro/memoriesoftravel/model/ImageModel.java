package com.cedro.memoriesoftravel.model;

import android.widget.ImageView;

/**
 * Created by emerson on 06/10/16.
 */

public  class ImageModel
{
    private String url;

    private ImageView imageView;

    public ImageModel(String u, ImageView i){
        url=u;
        imageView=i;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



}