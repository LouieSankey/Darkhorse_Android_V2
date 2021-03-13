package com.example.louissankey.darkhorselayouts.Activity.IntroActivities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.louissankey.darkhorselayouts.Math.Statistics;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;
//import com.kinvey.android.Client;
//import com.kinvey.android.callback.KinveyPingCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class Splash extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_spash);

        final StatPrices statPrices = getStatPrices(loadPlayersFromAsset());


        Button button = (Button) findViewById(R.id.splashButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Splash.this, Welcome.class);
                intent.putExtra(getString(R.string.STAT_PRICES), statPrices);

                startActivity(intent);
            }
        });

//        final Client mKinveyClient = new Client.Builder("kid_ryCcEZCX", "382bee828f794934b35fbca2de4f1646", this.getApplicationContext()).build();
//        mKinveyClient.ping(new KinveyPingCallback() {
//            @Override
//            public void onSuccess(Boolean aBoolean) {
//                Log.v("kinvey", "success");
//            }
//
//            @Override
//            public void onFailure(Throwable throwable) {
//                Log.v("kinvey", "failure");
//            }
//        });

    }



    public StatPrices getStatPrices(String jsonData){

        StatPrices statPrices = new StatPrices();
        double pt[], reb[], ast[], stl[], blk[], to[], _3[];


        try {
            JSONObject fullData = new JSONObject(jsonData);
            JSONArray jsonPlayers = fullData.getJSONArray("players");

            pt = new double[jsonPlayers.length()];
            reb  = new double[jsonPlayers.length()];
            stl = new double[jsonPlayers.length()];
            ast = new double[jsonPlayers.length()];
            blk  = new double[jsonPlayers.length()];
            _3  = new double[jsonPlayers.length()];
            to  = new double[jsonPlayers.length()];

            for (int i = 0; i < jsonPlayers.length(); i++) {

                JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);

                pt[i] = Double.parseDouble(jsonPlayer.getString("p/g"));
                reb[i] = Double.parseDouble(jsonPlayer.getString("r/g"));
                ast[i] = Double.parseDouble(jsonPlayer.getString("a/g"));
                blk[i] = Double.parseDouble(jsonPlayer.getString("b/g"));
                to[i] = Double.parseDouble(jsonPlayer.getString("to/g"));
                _3[i] = Double.parseDouble(jsonPlayer.getString("3/g"));
                stl[i] = Double.parseDouble(jsonPlayer.getString("s/g"));

            }

            double sPt  = 600 - new Statistics(pt).getStdDev()*100;
            double sReb = 600 - new Statistics(reb).getStdDev()*100;
            double sAst = 600 -new Statistics(ast).getStdDev()* 100;
            double sBlk = 600 - new Statistics(blk).getStdDev()*100;
            double sTo = 600 - new Statistics(to).getStdDev()*100;
            double s_3 = 600 - new Statistics(_3).getStdDev()*100;
            double sStl = 600 - new Statistics(stl).getStdDev()*100;


            statPrices.setPts(sPt);
            statPrices.setReb(sReb);
            statPrices.setAst(sAst);
            statPrices.setBlk(sBlk);
            statPrices.setTo(sTo);
            statPrices.set_3pt(s_3);
            statPrices.setStl(sStl);

        }catch (JSONException e) {
            e.printStackTrace();
        }

        return statPrices;
    }


    public String loadPlayersFromAsset() {
        String playerJSON = null;
        try {
            InputStream is = getAssets().open("MockPlayer.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            playerJSON = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }


        return playerJSON;
    }


}
