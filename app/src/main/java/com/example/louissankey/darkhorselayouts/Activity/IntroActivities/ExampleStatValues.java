package com.example.louissankey.darkhorselayouts.Activity.IntroActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Activity.Contests;
import com.example.louissankey.darkhorselayouts.R;

import java.text.DecimalFormat;

public class ExampleStatValues extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_example_stat_values);

        Button button = (Button) findViewById(R.id.statValuesButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExampleStatValues.this, Contests.class);
                startActivity(intent);

            }
        });


        TextView ptPrice = (TextView) findViewById(R.id.ptsPrice);
        TextView rebPrice = (TextView) findViewById(R.id.rebPrice);
        TextView stlPrice = (TextView) findViewById(R.id.stlPrice);
        TextView blkPrice = (TextView) findViewById(R.id.blkPrice);
        TextView astPrice = (TextView) findViewById(R.id.astPrice);
        TextView thrPrice = (TextView) findViewById(R.id.thrPrice);
        TextView toPrice = (TextView) findViewById(R.id.toPrice);

        DecimalFormat formatter = new DecimalFormat("$#,###");
        ptPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string.pts))));
        rebPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string.reb))));
        stlPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string.stl))));
        blkPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string.blk))));
        astPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string.ast))));
        thrPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string._3Pt))));
        toPrice.setText(formatter.format(Double.parseDouble(getResources().getString(R.string.to))));
    }
}
