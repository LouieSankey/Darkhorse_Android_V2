package com.example.louissankey.darkhorselayouts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Math.Statistics;
import com.example.louissankey.darkhorselayouts.Model.ContestItem;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Adapter.DraftListAdapter;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DraftList extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public static final String PLAYER_NAME = "PLAYER_NAME";
    public static final String SELECTED_PLAYER = "PLAYER";

    private ArrayList<Player> tonightsPlayers;
    private ArrayList<Player> mOpponents;
    private long mDraftTimeRemaining;
    private String mAccepted;
    private StatPrices mPrices;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_list);

        final TextView draftEnds = (TextView) findViewById(R.id.draftListDraftEnding);
        TextView accepting = (TextView) findViewById(R.id.draftListAccepting);


        Bundle extras = getIntent().getExtras();

        mOpponents = extras.getParcelableArrayList(Contests.OPPONENTS);
        mPrices = extras.getParcelable(getString(R.string.STAT_PRICES));

        tonightsPlayers = getPlayers(loadPlayersFromAsset());
        DraftListAdapter adapter = new DraftListAdapter(this, tonightsPlayers);
        ListView list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);


        mDraftTimeRemaining = extras.getLong("DRAFT_TIME_REMAINING");
        mAccepted = extras.getString("ACCEPTED");
        accepting.setText("Accepted: " + mAccepted);

        new CountDownTimer(mDraftTimeRemaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long durationSeconds = millisUntilFinished / 1000;

                mDraftTimeRemaining = millisUntilFinished;
                draftEnds.setText((String.format("%02d:%02d:%02d", durationSeconds / 3600,
                        (durationSeconds % 3600) / 60, (durationSeconds % 60))));

            }

            @Override
            public void onFinish() {
                draftEnds.setText("Finished");
            }
        }.start();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle extras = new Bundle();

        Player selectedPlayer = tonightsPlayers.get(position);

        if(!selectedPlayer.isDraftedOpponent()) {
            //todo: for testing sets the main username here
            selectedPlayer.setUsername("username21");
        }

        //for use determining which is the parent activity
        extras.putString(DraftPlayer.ACTIVITY, "DRAFT_LIST_ACTIVITY");


        Intent intent = new Intent(DraftList.this, DraftPlayer.class);
        intent.putExtra(getString(R.string.STAT_PRICES), mPrices);
        intent.putExtra("DRAFT_TIME_REMAINING", mDraftTimeRemaining);
        intent.putExtra("PLAYER", selectedPlayer);
        intent.putExtra("ACCEPTED", mAccepted);
        intent.putExtra(Contests.OPPONENTS, mOpponents);
            

        intent.putExtras(extras);
        startActivity(intent);

    }

    private ArrayList<Player> getPlayers(String jsonData) {
         ArrayList<Player> mPlayers = new ArrayList<>();

        try {
            JSONObject fullData = new JSONObject(jsonData);
            JSONArray players = fullData.getJSONArray("players");

            double max = topPositionModelPlayer(jsonData);
            double  min = 0.0;

            for (int i = 0; i < players.length(); i++){

                JSONObject jsonPlayer = players.getJSONObject(i);

                Player singlePlayer = new Player();

                double normalizedValue = normalize(Double.parseDouble(jsonPlayer.getString("Value")), min, max);

                singlePlayer.setPlayerName(jsonPlayer.getString("Name"));
                singlePlayer.setPrice(normalizedValue + "");
                singlePlayer.setPtsAvg(jsonPlayer.getString("p/g"));
                singlePlayer.setRebAvg(jsonPlayer.getString("r/g"));
                singlePlayer.setAstAvg(jsonPlayer.getString("a/g"));
                singlePlayer.setStlAvg(jsonPlayer.getString("s/g"));
                singlePlayer.setBlkAvg(jsonPlayer.getString("b/g"));
                singlePlayer.set_3PtAvg(jsonPlayer.getString("3/g"));
                singlePlayer.setTOAvg(jsonPlayer.getString("to/g"));

                for(int j = 0; j < mOpponents.size(); j++) {
                    Player opponent = mOpponents.get(j);

                    if (jsonPlayer.getString("Name").equals(opponent.getPlayerName())) {
                        singlePlayer.setUsername(opponent.getUsername());
                        singlePlayer.setDraftedOpponent(true);

                    }
                }

                mPlayers.add(singlePlayer);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return mPlayers;
    }

    public Double topPositionModelPlayer(String jsonData){

        Double topValueBound;
        Double pt, reb, ast, stl, blk, to, _3;
        pt = 0.0;
        reb = 0.0;
        ast = 0.0;
        blk = 0.0;
        stl = 0.0;
        _3 = 0.0;
        to = 0.0;

        try {
            JSONObject fullData = new JSONObject(jsonData);
            JSONArray jsonPlayers = fullData.getJSONArray("players");

            for (int i = 0; i < jsonPlayers.length(); i++) {
                JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);

                if (Double.parseDouble(jsonPlayer.getString("pV")) > pt) {
                   pt = Double.parseDouble(jsonPlayer.getString("pV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("rV")) > reb) {
                   reb = Double.parseDouble(jsonPlayer.getString("rV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("aV")) > ast) {
                    ast = Double.parseDouble(jsonPlayer.getString("aV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("sV")) > stl) {
                    stl = Double.parseDouble(jsonPlayer.getString("sV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("3V")) > _3) {
                    _3 = Double.parseDouble(jsonPlayer.getString("3V"));
                }
                if (Double.parseDouble(jsonPlayer.getString("toV")) > to) {
                    to = Double.parseDouble(jsonPlayer.getString("toV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("bV")) > blk) {
                    blk = Double.parseDouble(jsonPlayer.getString("bV"));
                }

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        topValueBound = ((pt-2)+(reb-2)+(ast-2)+(stl-2)+(blk-2)+(_3-2)+(to-2)) / 7;
      /*  Log.v("_pt", pt + "");
        Log.v("_reb", reb + "");
        Log.v("_ast", ast + "");
        Log.v("_stl", stl + "");
        Log.v("_blk", blk + "");
        Log.v("_3", _3 + "");
        Log.v("to", to + "");*/


        return topValueBound;
    }

    public Double bottomPositionModelPlayer(String jsonData){

        Double bottomValueBound;
        Double pt, reb, ast, stl, blk, to, _3;
        pt = 0.0;
        reb = 0.0;
        ast = 0.0;
        blk = 0.0;
        stl = 0.0;
        _3 = 0.0;
        to = 0.0;

        try {
            JSONObject fullData = new JSONObject(jsonData);
            JSONArray jsonPlayers = fullData.getJSONArray("players");

            for (int i = 0; i < jsonPlayers.length(); i++) {
                JSONObject jsonPlayer = jsonPlayers.getJSONObject(i);

                if (Double.parseDouble(jsonPlayer.getString("pV")) < pt) {
                    pt = Double.parseDouble(jsonPlayer.getString("pV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("rV")) < reb) {
                    reb = Double.parseDouble(jsonPlayer.getString("rV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("aV")) > ast) {
                    ast = Double.parseDouble(jsonPlayer.getString("aV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("sV")) < stl) {
                    stl = Double.parseDouble(jsonPlayer.getString("sV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("3V")) < _3) {
                    _3 = Double.parseDouble(jsonPlayer.getString("3V"));
                }
                if (Double.parseDouble(jsonPlayer.getString("toV")) < to) {
                    to = Double.parseDouble(jsonPlayer.getString("toV"));
                }
                if (Double.parseDouble(jsonPlayer.getString("bV")) < blk) {
                    blk = Double.parseDouble(jsonPlayer.getString("bV"));
                }

            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        bottomValueBound = (pt+reb+ast+stl+blk+_3+to) / 7;

        return bottomValueBound;
    }

    //normalizes values betwen a range

    public double normalize(double value, double min, double max) {
        double convertedValue;

        convertedValue = .5 + (value - min) * (.95 - .5) / (max - min);

        return convertedValue;

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

    private void setCountdownTimer(long i, final TextView countdownTextView) {


    }


}
