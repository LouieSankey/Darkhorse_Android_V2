package com.example.louissankey.darkhorselayouts.Activity.IntroActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Activity.Contests;
import com.example.louissankey.darkhorselayouts.Math.Statistics;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class StatValues extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_stat_values);

        Bundle extras = getIntent().getExtras();
        final StatPrices prices = extras.getParcelable(getString(R.string.STAT_PRICES));

        TextView ptPrice = (TextView) findViewById(R.id.ptsPrice);
        TextView rebPrice = (TextView) findViewById(R.id.rebPrice);
        final TextView stlPrice = (TextView) findViewById(R.id.stlPrice);
        TextView blkPrice = (TextView) findViewById(R.id.blkPrice);
        TextView astPrice = (TextView) findViewById(R.id.astPrice);
        TextView thrPrice = (TextView) findViewById(R.id.thrPrice);
        TextView toPrice = (TextView) findViewById(R.id.toPrice);

        DecimalFormat formatter = new DecimalFormat("$#,###");
        ptPrice.setText(formatter.format(prices.getPts()));
        rebPrice.setText(formatter.format(prices.getReb()));
        stlPrice.setText(formatter.format(prices.getStl()));
        blkPrice.setText(formatter.format(prices.getBlk()));
        astPrice.setText(formatter.format(prices.getAst()));
        thrPrice.setText(formatter.format(prices.get_3pt()));
        toPrice.setText(formatter.format(prices.getTo()));

        Button toLobby = (Button) findViewById(R.id.toLobby);
        toLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatValues.this, Contests.class);
                intent.putExtra(getString(R.string.STAT_PRICES), prices);
                startActivity(intent);


            }
        });
    }





}
