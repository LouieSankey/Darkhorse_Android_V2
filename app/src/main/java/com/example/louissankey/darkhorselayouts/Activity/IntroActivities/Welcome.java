package com.example.louissankey.darkhorselayouts.Activity.IntroActivities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;

public class Welcome extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_welcome);

        Bundle extras = getIntent().getExtras();

       final StatPrices statPrices = extras.getParcelable(getString(R.string.STAT_PRICES));



        Button toStatValues = (Button) findViewById(R.id.toStatValues);
        toStatValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Welcome.this, StatValues.class);
                intent.putExtra(getString(R.string.STAT_PRICES), statPrices);
                startActivity(intent);
            }
        });

    }
}
