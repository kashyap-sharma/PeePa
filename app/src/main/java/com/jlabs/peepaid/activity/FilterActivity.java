package com.jlabs.peepaid.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.jlabs.peepaid.R;
import com.jlabs.peepaid.customcomponents.ButtonModarno;

public class FilterActivity extends AppCompatActivity {
    ButtonModarno filterapply;
    Context con;
    LinearLayout women, men,both;
    RadioButton wom_swi,men_swi,bot_swi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        con = this;
        women=(LinearLayout)findViewById(R.id.women);
        men=(LinearLayout)findViewById(R.id.men);
        both=(LinearLayout)findViewById(R.id.both);
        wom_swi=(RadioButton)findViewById(R.id.wom_swi);
        men_swi=(RadioButton)findViewById(R.id.man_swi);
        bot_swi=(RadioButton)findViewById(R.id.bot_swi);
        filterapply = (ButtonModarno) findViewById(R.id.filterapply);

        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wom_swi.setChecked(true);
                men_swi.setChecked(false);
                bot_swi.setChecked(false);

            }
        });
        men.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                men_swi.setChecked(true);
                wom_swi.setChecked(false);
                bot_swi.setChecked(false);

            }
        });
        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wom_swi.setChecked(false);
                men_swi.setChecked(false);
                bot_swi.setChecked (true);

            }
        });











        filterapply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(con, Main22Activity.class);
                startActivity(i);

            }
        });


    }

}
