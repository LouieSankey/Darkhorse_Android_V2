package com.example.louissankey.darkhorselayouts.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Adapter.DashboardAdapter;
import com.example.louissankey.darkhorselayouts.Model.BuyCountdown;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView leagueListView;
    private Bundle mExtras;
    private ArrayList<Player> mOpponents;
    private Player mSelectedPlayer;
    private long mDraftTimeRemaining;
    private BuyCountdown mBuyCountdown;
    private StatPrices mPrices;


    public static final String USERNAME = "USERNAME";


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mExtras = getIntent().getExtras();
        mSelectedPlayer = mExtras.getParcelable(DraftList.SELECTED_PLAYER);
        mOpponents = mExtras.getParcelableArrayList(Contests.OPPONENTS);
        mDraftTimeRemaining = mExtras.getLong("DRAFT_TIME_REMAINING");
        mPrices = mExtras.getParcelable(getString(R.string.STAT_PRICES));



        mBuyCountdown = new BuyCountdown();
        mBuyCountdown.setBuyTimeRemaining(mDraftTimeRemaining + (60000 * 30));
        //mBuyCountdown.setTimeIn(tsLong);

        setDraftCountdown(mDraftTimeRemaining);
        setBuyCountdown(mDraftTimeRemaining + (60000 * 30));
    }

    @Override
    protected void onResume() {
        super.onResume();


        ShimmerTextView playerDrafted = (ShimmerTextView) findViewById(R.id.playerDraftedIndicator);
        Shimmer shimmer = new Shimmer();
        shimmer.start(playerDrafted);

        final TextView playerUsername = (TextView) findViewById(R.id.playerUsername);
        TextView playerNameTextView = (TextView) findViewById(R.id.dashboardPlayerName);
        TextView playerPriceTextView = (TextView) findViewById(R.id.dashboardPrice);
        TextView playerCapTextView = (TextView) findViewById(R.id.dashboardCap);
        TextView accepted = (TextView) findViewById(R.id.dashboardAccepted);
        accepted.setText("Accepted: " + mExtras.getString("ACCEPTED"));

        Button contestStandings = (Button) findViewById(R.id.toStandings);
        contestStandings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Standings.class);
                intent.putExtras(mExtras);
                startActivity(intent);

            }
        });

        String playerName = mSelectedPlayer.getPlayerName();

        playerUsername.setText(mSelectedPlayer.getUsername());
        playerNameTextView.setText(playerName);
        playerPriceTextView.setText("Game Price: " + mSelectedPlayer.getPrice());
        playerCapTextView.setText("Remaining Funds: " + mSelectedPlayer.getCapString());

        ImageView dashboardImageView = (ImageView) findViewById(R.id.dashboardImageView);
        String playerUrlName = getPlayerUrlName(playerName);
        Picasso.with(this).load(
                "http://d2cwpp38twqe55.cloudfront.net/images/images-002/players/"
                        + playerUrlName
                        + ".png");


        leagueListView =  (ListView) findViewById(R.id.leagueListView2);


        DashboardAdapter listAdapter = new DashboardAdapter(this, mOpponents, mSelectedPlayer, mBuyCountdown, mPrices);

        leagueListView.setAdapter(listAdapter);
    }

    private void setDraftCountdown(long i) {
        final TextView draftCountdown = (TextView) findViewById(R.id.matchUpDraftCountdown);


        new CountDownTimer(i, 1000) {

            public void onTick(long millisUntilFinished) {

                long durationSeconds = millisUntilFinished / 1000;

                draftCountdown.setText(String.format("%02d:%02d:%02d", durationSeconds / 3600,
                        (durationSeconds % 3600) / 60, (durationSeconds % 60)));
            }

            public void onFinish() {
                draftCountdown.setText("Draft Over");
                setBuyCountdown(60000 * 30);
            }

        }.start();

    }

    private void setBuyCountdown(long i) {

        final TextView buyCountdown = (TextView) findViewById(R.id.matchUpBuyCountdown);

        new CountDownTimer(i, 1000) {

            public void onTick(long millisUntilFinished) {

                long durationSeconds = millisUntilFinished / 1000;

                //mBuyCountdown.setBuyTimeRemaining(millisUntilFinished);

                if(millisUntilFinished > (60000 * 30)){
                    buyCountdown.setText("00:30:00");
                }else {
                    buyCountdown.setText(String.format("%02d:%02d:%02d", durationSeconds / 3600,
                            (durationSeconds % 3600) / 60, (durationSeconds % 60)));
                }
            }

            public void onFinish() {
                buyCountdown.setText("Finished!");
                setBuyCountdown(60000 * 30);
            }

        }.start();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(Dashboard.this, BuyAgainst.class);
        startActivity(intent);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Dashboard.this, Contests.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
