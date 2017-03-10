package com.jlabs.peepaid.customcomponents;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class TextView_Black1 extends TextView {


    public TextView_Black1(Context context) {
      super(context);
        Typeface tf = FontCache.get("fonts/icomoonaa.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    public TextView_Black1(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface tf = FontCache.get("fonts/icomoonaa.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    public TextView_Black1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface tf = FontCache.get("fonts/icomoonaa.ttf", context);
        if(tf != null) {
            this.setTypeface(tf);

        }
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }

}