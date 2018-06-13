package com.avengers.intfacebase.image;

import android.widget.ImageView;

import java.net.URI;

public interface ImgCommon {

    void loadImg(ImageView imageView, URI uri);

    void loadImg(ImageView imageView, URI uri, CallBack callback);

}
