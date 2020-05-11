package com.example.louissankey.darkhorselayouts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.louissankey.darkhorselayouts.Adapter.ContestAdapter;
import com.example.louissankey.darkhorselayouts.Model.ContestItem;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Contests extends AppCompatActivity {

    public static final String OPPONENTS = "OPPONENTS";

    private List<ContestItem> mContests;
    private LinkedHashMap<ContestItem, List<Player>> mContestOpponentMap;
    private ExpandableListView mContestListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        Bundle extras = getIntent().getExtras();

        prepareListData();

        mContestListView =  (ExpandableListView) findViewById(R.id.contestListView);
        ContestAdapter contestAdapter = new ContestAdapter(this, mContests, mContestOpponentMap, extras);
        mContestListView.setAdapter(contestAdapter);


        mContestListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                List<List<Player>> player = new ArrayList<>(mContestOpponentMap.values());
                Player opponent =  player.get(groupPosition).get(childPosition);
                ContestItem contest = mContests.get(groupPosition);

                        Log.v("groupPosition", groupPosition + "");

                        Bundle extras = getIntent().getExtras();
                        extras.putString(DraftPlayer.ACTIVITY, "LOBBY_ACTIVITY");
                        Intent intent = new Intent(Contests.this, DraftPlayer.class);
                        intent.putExtra("PLAYER", opponent);
                        intent.putExtra("DRAFT_TIME_REMAINING", contest.getDraftEnds());
                        intent.putExtra("ACCEPTED", contest.getAccepting());
                        intent.putExtras(extras);
                        startActivity(intent);

                return false;
            }
        });

    }


    private void prepareListData() {
        double max = topPositionModelPlayer(loadPlayersFromAsset());
        String players = loadPlayersFromAsset();

        mContests = new ArrayList<>();
        mContestOpponentMap = new LinkedHashMap<>();


        //EXAMPLE CONTEST 1

        ArrayList<String> examplePlayers1 = new ArrayList<>();
        examplePlayers1.add("Paul George");
        examplePlayers1.add("Paul Millsap");
        examplePlayers1.add("Damian Lillard");
        examplePlayers1.add("Kyrie Irving");

        int totalAccepting = 7;

        ArrayList<Player> Players1 = getOpponents(players, examplePlayers1, totalAccepting, max);

        ContestItem Contest1 = new ContestItem();
        Contest1.setGameType("* NBA $10 Round Royal 1 on 1");
        Contest1.setAccepting((examplePlayers1.size()) + "/" + totalAccepting);
        Contest1.setNbaGamesAmnt("6");
        Contest1.setDraftCountdown(60000 * 3);
        mContests.add(Contest1);


        //EXAMPLE CONTEST 2

        ArrayList<String> examplePlayers2 = new ArrayList<>();
        examplePlayers2.add("Kevin Durant");
        examplePlayers2.add("LeBron James");

        int totalAccepting2 = 5;

        ArrayList<Player> Players2 = getOpponents(players, examplePlayers2, totalAccepting2, max);

        ContestItem Contest2 = new ContestItem();
        Contest2.setGameType("* NBA $30 Round Royal 1 on 1");
        Contest2.setAccepting((examplePlayers2.size()) + "/" + totalAccepting2);
        Contest2.setDraftCountdown(60000 * 5);
        Contest2.setNbaGamesAmnt("4");

        mContests.add(Contest2);


        //EXAMPLE CONTEST 3

        ArrayList<String> examplePlayers3 = new ArrayList<>();
        examplePlayers3.add("Anthony Davis");
        examplePlayers3.add("Kyle Lowry");
        examplePlayers3.add("Jimmy Butler");
        examplePlayers3.add("Kevin Love");
        examplePlayers3.add("Isaiah Thomas");
        examplePlayers3.add("Danny Green");


        int totalAccepting3 = 7;

        ArrayList<Player> Players3 = getOpponents(players, examplePlayers3, totalAccepting3, max);

        ContestItem Contest3 = new ContestItem();
        Contest3.setGameType("* NBA $3 Round Royal 1 on 1");
        Contest3.setAccepting((examplePlayers3.size()) + "/" + totalAccepting3);
        Contest3.setDraftCountdown(60000 * 1);
        Contest3.setNbaGamesAmnt("4");
        mContests.add(Contest3);


        //EXAMPLE CONTEST 4

        ArrayList<String> examplePlayers4 = new ArrayList<>();
        examplePlayers4.add("Anthony Davis");

        int totalAccepting4 = 2;

        ArrayList<Player> Players4 = getOpponents(players, examplePlayers4, totalAccepting4, max);

        ContestItem Contest4 = new ContestItem();
        Contest4.setGameType("* NBA $3 Heads Up 1 on 1");
        Contest4.setAccepting((examplePlayers4.size()) + "/" + totalAccepting4);
        Contest4.setDraftCountdown(60000 * 2);
        Contest4.setNbaGamesAmnt("6");
        mContests.add(Contest4);


        //EXAMPLE CONTEST 5

        ArrayList<String> examplePlayers5 = new ArrayList<>();

        int totalAccepting5 = 2;

        ArrayList<Player> Players5 = getOpponents(players, examplePlayers5, totalAccepting5, max);

        ContestItem Contest5 = new ContestItem();
        Contest5.setGameType("* NBA $20 Heads Up 1 on 1");
        Contest5.setAccepting((examplePlayers5.size()) + "/" + totalAccepting5);
        Contest5.setDraftCountdown(60000 * 8);
        Contest5.setNbaGamesAmnt("1");
        mContests.add(Contest5);


        ArrayList<Map> contestOpponent = new ArrayList<>();

        mContestOpponentMap.put(mContests.get(0), Players1);
        mContestOpponentMap.put(mContests.get(1), Players2);
        mContestOpponentMap.put(mContests.get(2), Players3);
        mContestOpponentMap.put(mContests.get(3), Players4);
        mContestOpponentMap.put(mContests.get(4), Players5);


    }




    //for testing only, will be replaced with real data
    private ArrayList<Player> getOpponents(String jsonData, ArrayList<String> playerName, int totalAccepting, double max) {
        ArrayList<Player> opponents = new ArrayList<>();
        Player header = new Player();
        header.setDisplayType(2);
        opponents.add(header);


        try {
            JSONObject fullData = new JSONObject(jsonData);
            JSONArray players = fullData.getJSONArray("players");

            //double max = topPositionModelPlayer(jsonData);
            double  min = 0.0;

            for (int i = 0; i < players.length(); i++){

                JSONObject jsonPlayer = players.getJSONObject(i);

                double normalizedValue = normalize(Double.parseDouble(jsonPlayer.getString("Value")), min, max);

                if(playerName.contains(jsonPlayer.getString("Name"))){

                    Player opponent1 = new Player();
                    opponent1.setPlayerName(jsonPlayer.getString("Name"));
                    opponent1.setPrice(normalizedValue + "");
                    opponent1.setPtsAvg(jsonPlayer.getString("p/g"));
                    opponent1.setRebAvg(jsonPlayer.getString("r/g"));
                    opponent1.setAstAvg(jsonPlayer.getString("a/g"));
                    opponent1.setStlAvg(jsonPlayer.getString("s/g"));
                    opponent1.setBlkAvg(jsonPlayer.getString("b/g"));
                    opponent1.set_3PtAvg(jsonPlayer.getString("3/g"));
                    opponent1.setTOAvg(jsonPlayer.getString("to/g"));
                    opponent1.setUsername("user " + i +"39944");
                    opponent1.setOppVersus("HTM @ AWT");
                    opponent1.setDraftedOpponent(true);
                    opponent1.setDisplayType(1);
                    opponents.add(opponent1);

                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        while (opponents.size() < totalAccepting + 1){
            Player opponent = new Player();
            opponent.setDisplayType(3);
            opponents.add(opponent);
        }

        Player draftButton = new Player();
        draftButton.setDisplayType(4);
        opponents.add(draftButton);


        return opponents;
    }

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

        return topValueBound;
    }

    /*public Double bottomPositionModelPlayer(String jsonData){

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
    }*/



}
