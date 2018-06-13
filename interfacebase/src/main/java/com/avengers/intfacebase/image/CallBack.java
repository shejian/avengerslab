package com.avengers.intfacebase.image;

import android.graphics.drawable.Drawable;

public interface CallBack {

    void okload(Drawable drawable);

    void error(Exception e);


}
