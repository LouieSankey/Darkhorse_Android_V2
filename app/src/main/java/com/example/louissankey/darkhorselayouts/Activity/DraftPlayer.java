package com.example.louissankey.darkhorselayouts.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DraftPlayer extends AppCompatActivity {

    public static final String ACTIVITY = "ACTIVITY";
    private Bundle mExtras;
    private String mPlayerName;
    private Player mPlayer;
    private long mDraftTimeRemaining;
    private String mAccepted;
    private StatPrices mPrices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draft_player);

        mExtras = getIntent().getExtras();


        mPlayer = mExtras.getParcelable("PLAYER");
        mPlayerName = mPlayer.getPlayerName();
        mDraftTimeRemaining = mExtras.getLong("DRAFT_TIME_REMAINING");
        mAccepted = mExtras.getString("ACCEPTED");
        mPrices = mExtras.getParcelable(getString(R.string.STAT_PRICES));



        final TextView draftEnds = (TextView) findViewById(R.id.draftEndingCountdown);
        final TextView username = (TextView) findViewById(R.id.profileUsername);

        TextView accepting = (TextView) findViewById(R.id.draftPlayerAccepted);
        accepting.setText("Accepted: "+ mAccepted);




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



        TextView ptsPrice = (TextView) findViewById(R.id.profilePtsPrice);
        TextView rebPrice = (TextView) findViewById(R.id.profileRebPrice);
        TextView astPrice = (TextView) findViewById(R.id.profileAstPrice);
        TextView stlPrice = (TextView) findViewById(R.id.profileStlPrice);
        TextView blkPrice = (TextView) findViewById(R.id.profileBlkPrice);
        TextView _3PtPrice = (TextView) findViewById(R.id.profile3PtPrice);
        TextView toPrice = (TextView) findViewById(R.id.profileTOPrice);

        TextView playerNameTextView = (TextView) findViewById(R.id.profileNameTextView);
        TextView playerCapTextView = (TextView) findViewById(R.id.profileFundsRemainingTextView);
        TextView playerPriceTextView = (TextView) findViewById(R.id.profilePlayerPriceTextView);

        final ShimmerTextView opponentDrafted = (ShimmerTextView) findViewById(R.id.opponentDraftedTextView);

        TextView ptsAvg = (TextView) findViewById(R.id.ptsAvgTextView);
        TextView rebAvg = (TextView) findViewById(R.id.rebAvgTextView);
        TextView astAvg = (TextView) findViewById(R.id.astAvgTextView);
        TextView stlAvg = (TextView) findViewById(R.id.stlAvgTextView);
        TextView blkAvg = (TextView) findViewById(R.id.blkAvgTextView);
        TextView _3PtAvg = (TextView) findViewById(R.id._3PtAvgTextView);
        TextView toAvg = (TextView) findViewById(R.id.toAvgTextView);

        DecimalFormat formatter = new DecimalFormat("$#,###");

        ptsPrice.setText(formatter.format(mPrices.getPts()));
        rebPrice.setText(formatter.format(mPrices.getReb()));
        astPrice.setText(formatter.format(mPrices.getAst()));
        stlPrice.setText(formatter.format(mPrices.getStl()));
        blkPrice.setText(formatter.format(mPrices.getBlk()));
        _3PtPrice.setText(formatter.format(mPrices.get_3pt()));
        toPrice.setText(formatter.format(mPrices.getTo()));

        playerNameTextView.setText(mPlayerName);
        String playerCap = mPlayer.getCapString();
        playerPriceTextView.setText("Game Price: " + mPlayer.getPrice());
        playerCapTextView.setText("Cap Space: " + playerCap);

        ptsAvg.setText(mPlayer.getPtsAvg());
        rebAvg.setText(mPlayer.getRebAvg());
        astAvg.setText(mPlayer.getAstAvg());
        stlAvg.setText(mPlayer.getStlAvg());
        blkAvg.setText(mPlayer.getBlkAvg());
        _3PtAvg.setText(mPlayer.get_3PtAvg());
        toAvg.setText(mPlayer.getTOAvg());


        String name = mPlayerName;
        ImageView profilePlayerImageView = (ImageView) findViewById(R.id.profilePlayerImageView);
        String playerUrlName = getPlayerUrlName(name);
        Picasso.with(this).load(
                        "http://d2cwpp38twqe55.cloudfront.net/images/images-002/players/"
                                + playerUrlName
                                + ".png");


        Button draftPlayerButton = (Button) findViewById(R.id.acceptPickButton);

        //TODO: refactor into own method
        String parseBySpaces = "[ ]+";
        String parseSelectedPlayerName[] = mPlayerName.split(parseBySpaces);
        String playerFirstName = parseSelectedPlayerName[0];
        String playerLastName = parseSelectedPlayerName[1];
        String firstInitial = playerFirstName.substring(0, 1) + ".";

        if(mPlayer.isDraftedOpponent()){
            username.setText(mPlayer.getUsername());
            username.setTextColor(ContextCompat.getColor(DraftPlayer.this, R.color.draftedOpponent));
            opponentDrafted.setVisibility(View.VISIBLE);
            Shimmer shimmer = new Shimmer();
            shimmer.start(opponentDrafted);
            draftPlayerButton.setText(firstInitial.toUpperCase() + " "
                    + playerLastName + " is Drafted");

        }else{
            username.setVisibility(View.VISIBLE);
            username.setText("");

            draftPlayerButton.setText("Enter with " + firstInitial.toUpperCase() + " "
                    + playerLastName);

        }


        draftPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mPlayer.isDraftedOpponent()){
                    Toast.makeText(DraftPlayer.this, mPlayerName + " has already been drafted for this contest!", Toast.LENGTH_SHORT).show();
                }else {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(DraftPlayer.this, R.style.alertDialogCustom);
                    dialog.setTitle("Draft " + mPlayerName + " and Enter Contest?")
                            //.setMessage("If there are not 7 users by the draft deadlin")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    Intent intent = new Intent(DraftPlayer.this, Dashboard.class);
                                    ArrayList<Player> opponents = mExtras.getParcelableArrayList(Contests.OPPONENTS);
                                    intent.putExtras(mExtras);

                                    //TODO: for testing, making it so the opponents list is the right size for the adapter
                                    if(opponents.size() == mExtras.getParcelableArrayList(Contests.OPPONENTS).size()){
                                        opponents.remove(opponents.size() -1);
                                    }

                                    intent.putExtra(Contests.OPPONENTS, opponents);
                                    intent.putExtra("DRAFT_TIME_REMAINING", mDraftTimeRemaining);
                                    intent.putExtra("ACCEPTED", incrementAcceptedPlayers(mAccepted));
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            });


                    dialog.show();
                }




            }
        });
    }

    private String incrementAcceptedPlayers(String accepted) {

        Integer incrementAccepted = Integer.parseInt(accepted.substring(0,1)) + 1;
        String updatedAccepted = incrementAccepted.toString() + accepted.substring(1,3);

        return updatedAccepted;
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

//For making the correct activity the parent activity. Either Lobby or DraftList

    @Override
    public Intent getSupportParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    @Override
    public Intent getParentActivityIntent() {
        return getParentActivityIntentImpl();
    }

    private Intent getParentActivityIntentImpl() {
        Intent intent = null;

        // Here you need to do some logic to determine from which Activity you came.
        // example: you could pass a variable through your Intent extras and check that.
        if (mExtras.getString(ACTIVITY).equals("LOBBY_ACTIVITY")) {
            intent = new Intent(this, Contests.class);
            // set any flags or extras that you need.
            // If you are reusing the previous Activity (i.e. bringing it to the top
            // without re-creating a new instance) set these flags:
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            // if you are re-using the parent Activity you may not need to set any extras
        } else if(mExtras.getString(ACTIVITY).equals("DRAFT_LIST_ACTIVITY")) {
            intent = new Intent(this, DraftList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }else{

        }

        return intent;
    }



}
