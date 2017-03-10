package com.jlabs.peepaid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jlabs.peepaid.R;
import com.skyfishjy.library.RippleBackground;

public class TestMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_me);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
    }
}
