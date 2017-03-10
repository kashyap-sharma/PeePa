package com.jlabs.peepaid.customcomponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class MyIconFonts extends TextView {


    public MyIconFonts(Context context) {
      super(context);
        Typeface tf = FontCache.get("fonts/materialdesignicons-webfont.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    public MyIconFonts(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = FontCache.get("fonts/materialdesignicons-webfont.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    public MyIconFonts(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/materialdesignicons-webfont.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}