package com.example.louissankey.darkhorselayouts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Adapter.ResultsAdapter;
import com.example.louissankey.darkhorselayouts.Model.MatchResultsItem;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Results extends AppCompatActivity {

    private ArrayList<MatchResultsItem> mResultsItems;
    private Player mOpponent;
    private Player mSelectedPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Bundle extras = getIntent().getExtras();

        mSelectedPlayer = extras.getParcelable("SELECTED_PLAYER");
        mOpponent = extras.getParcelable("OPPONENT");

        setBuyCountdown(extras.getLong("BUYING_ENDS"));

        Button nextOpp = (Button) findViewById(R.id.nextOppButton);
        TextView playerName = (TextView) findViewById(R.id.resultsPlayerName);



        TextView oppName = (TextView) findViewById(R.id.resultsOppName);

        playerName.setText(mSelectedPlayer.getPlayerName());

        oppName.setText(mOpponent.getPlayerName());

        String playerNameAbb = getPlayerNameAbbrev(mSelectedPlayer.getPlayerName());
        String oppNamAbb = getPlayerNameAbbrev(mOpponent.getPlayerName());


        nextOpp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Results.this, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        mResultsItems = new ArrayList<>();

        MatchResultsItem pts = new MatchResultsItem();
        pts.setStatCategory("Pts");
        pts.setPlayerName(playerNameAbb);
        pts.setOpponentName(oppNamAbb);
        pts.setPlayerBuy("+ " + extras.getString("Pts"));
        mResultsItems.add(pts);

        MatchResultsItem reb = new MatchResultsItem();
        reb.setStatCategory("Reb");
        reb.setPlayerName(playerNameAbb);
        reb.setOpponentName(oppNamAbb);
        reb.setPlayerBuy("+ " + extras.getString("Reb"));
        mResultsItems.add(reb);

        MatchResultsItem ast = new MatchResultsItem();
        ast.setStatCategory("Ast");
        ast.setPlayerName(playerNameAbb);
        ast.setOpponentName(oppNamAbb);
        ast.setPlayerBuy("+ " + extras.getString("Ast"));
        mResultsItems.add(ast);

        MatchResultsItem stl = new MatchResultsItem();
        stl.setStatCategory("Stl");
        stl.setPlayerName(playerNameAbb);
        stl.setOpponentName(oppNamAbb);
        stl.setPlayerBuy("+ " + extras.getString("Stl"));
        mResultsItems.add(stl);

        MatchResultsItem blk = new MatchResultsItem();
        blk.setStatCategory("Blk");
        blk.setPlayerName(playerNameAbb);
        blk.setOpponentName(oppNamAbb);
        blk.setPlayerBuy("+ " + extras.getString("Blk"));
        mResultsItems.add(blk);

        MatchResultsItem _3pt = new MatchResultsItem();
        _3pt.setStatCategory("3pt");
        _3pt.setPlayerName(playerNameAbb);
        _3pt.setOpponentName(oppNamAbb);
        _3pt.setPlayerBuy("+ " + extras.getString("3Pt"));
        mResultsItems.add(_3pt);

        MatchResultsItem to = new MatchResultsItem();
        to.setStatCategory("TO");
        to.setPlayerName(playerNameAbb);
        to.setOpponentName(oppNamAbb);
        to.setPlayerBuy("- " + extras.getString("-TO"));
        mResultsItems.add(to);

        ResultsAdapter adapter = new ResultsAdapter(this, mResultsItems);
        ListView listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);


    }

    public String getPlayerNameAbbrev(String playerName) {
        String parseBySpaces = "[ ]+";
        String parseSelectedPlayerName[] = playerName.split(parseBySpaces);
        String playerLastName = parseSelectedPlayerName[1];
        if (playerLastName.length() > 5) {
            String playerLastAbrev = playerLastName.substring(0, 4) + ".";
            return playerLastAbrev;
        } else {
            return playerLastName;
        }
    }

    private String getPlayerUrlName(String selectedPlayer) {
        String parseBySpaces = "[ ]+";
        String parseSelectedPlayerName[] = selectedPlayer.split(parseBySpaces);
        String playerFirstName = parseSelectedPlayerName[0];
        String playerLastName = parseSelectedPlayerName[1];

        if (playerLastName.length() >= 5) {
            playerLastName = playerLastName.substring(0, 5);
        }

        playerFirstName = playerFirstName.substring(0, 2);

        String playerKey = playerLastName + playerFirstName;
        playerKey = playerKey.toLowerCase();

        if (playerKey.equals("matthwe")
                || playerKey.equals("davisan")
                || playerKey.equals("greenda")
                || playerKey.equals("hillge")) {
            playerKey = playerKey + "02";
        } else {
            playerKey = playerKey + "01";
        }

        return playerKey;

    }

    private void setBuyCountdown(long i) {
        final TextView buyingEndsTextView = (TextView) findViewById(R.id.resultsBuyingEnds);

        new CountDownTimer(i, 1000) {

            public void onTick(long millisUntilFinished) {

                long durationSeconds = millisUntilFinished / 1000;

                if(millisUntilFinished > (60000 * 30)){
                    buyingEndsTextView.setText("00:30:00");
                }else {
                    buyingEndsTextView.setText(String.format("%02d:%02d:%02d", durationSeconds / 3600,
                            (durationSeconds % 3600) / 60, (durationSeconds % 60)));
                }


            }

            public void onFinish() {
                buyingEndsTextView.setText("Buying Finished");
            }

        }.start();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Results.this, Dashboard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
