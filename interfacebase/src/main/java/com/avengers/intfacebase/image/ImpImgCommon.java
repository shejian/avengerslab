package com.avengers.intfacebase.image;

import android.widget.ImageView;

import com.avengers.zombiebase.BaseCommon;

import java.net.URI;

public class ImpImgCommon implements ImgCommon {
    @Override
    public void loadImg(ImageView imageView, URI uri) {
        BaseCommon baseCommon = new BaseCommon();
        baseCommon.load(imageView, String.valueOf(uri));
    }

    @Override
    public void loadImg(ImageView imageView, URI uri, CallBack callback) {

    }
}
