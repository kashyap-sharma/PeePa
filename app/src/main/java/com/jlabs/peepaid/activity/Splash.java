package com.jlabs.peepaid.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.jlabs.peepaid.R;
import com.jlabs.peepaid.functions.Static_Catelog;
import com.jrummyapps.android.widget.AnimatedSvgView;


public class Splash extends Activity {
    Activity context;

    private AnimatedSvgView svgView;
    public static int splash_time = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);
        context=this;

        String checkit = Static_Catelog.getStringProperty(context, "checkin");
        svgView = (AnimatedSvgView) findViewById(R.id.animated_svg_view);

        svgView.postDelayed(new Runnable() {

            @Override
            public void run() {
                svgView.start();
            }
        }, 500);

        svgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (svgView.getState() == AnimatedSvgView.STATE_FINISHED) {
                    svgView.start();
                }
            }
        });
        Log.i("hippo", "" + checkit);
        if (checkit == null) {

            final Intent myIntent = new Intent(this, LoginActivity.class);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(myIntent);
                    context.finish();
                }
            }, splash_time);
        }else{
            final Intent myIntent = new Intent(this, Main22Activity.class);
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    startActivity(myIntent);
                    context.finish();
                }
            }, splash_time);
        }

    }


}
