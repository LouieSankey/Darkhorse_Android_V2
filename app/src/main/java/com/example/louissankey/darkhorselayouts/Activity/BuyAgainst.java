package com.example.louissankey.darkhorselayouts.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Adapter.BuyAgainstAdapter;
import com.example.louissankey.darkhorselayouts.Model.BuyStatsListItem;
import com.example.louissankey.darkhorselayouts.Model.BuyStatsSubListItem;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyAgainst extends AppCompatActivity {

    private BuyAgainstAdapter listAdapter;
    private List<BuyStatsListItem> buyStatsListItems;
    private HashMap<BuyStatsListItem, BuyStatsSubListItem> buyStatSubListItem;
    private ExpandableListView expListView;
    private Button finishButton;
    private Bundle mExtras;
    private Player mOpponent;
    private Player mSelectedPlayer;
    private StatPrices mPrices;


    public static TextView dynamicFunds;
    private long mBuyingEnds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_against);

        mExtras = getIntent().getExtras();
        mOpponent = mExtras.getParcelable("OPPONENT");
        mSelectedPlayer = mExtras.getParcelable("SELECTED_PLAYER");
        mBuyingEnds = mExtras.getLong("BUYING_ENDS");
        mPrices = mExtras.getParcelable(getString(R.string.STAT_PRICES));

        long timeIn = mExtras.getLong("LONG_TIME_IN");
        long timeSync = System.currentTimeMillis();
        setBuyCountdown(mBuyingEnds - (timeSync - timeIn));

        final TextView buyForPlayerName = (TextView) findViewById(R.id.buyForPlayerName);
        TextView playerNameAbrev = (TextView) findViewById(R.id.playerNameAbrev);

        TextView opponentNameAbrev = (TextView) findViewById(R.id.opponentNameAbrev);
        TextView oppName = (TextView) findViewById(R.id.oppName);
        TextView oppPrice = (TextView) findViewById(R.id.oppPrice);
        TextView oppCap = (TextView) findViewById(R.id.oppCap);
        TextView oppUsername = (TextView) findViewById(R.id.oppUsername);


        ImageView oppImageView = (ImageView) findViewById(R.id.oppImageView);
        String oppUrlName = getPlayerUrlName(mOpponent.getPlayerName());

        Picasso.with(this).load(
                "http://d2cwpp38twqe55.cloudfront.net/images/images-002/players/"
                        + oppUrlName
                        + ".png").into(oppImageView);



        buyForPlayerName.setText(mSelectedPlayer.getPlayerName());
        playerNameAbrev.setText(getPlayerNameAbbrev(mSelectedPlayer.getPlayerName()));
        opponentNameAbrev.setText(getPlayerNameAbbrev(mOpponent.getPlayerName()));
        oppUsername.setText(mOpponent.getUsername());

        oppName.setText(mOpponent.getPlayerName());
        oppPrice.setText(mOpponent.getPrice());
        oppCap.setText(mOpponent.getCapString());




        finishButton = (Button) findViewById(R.id.finishButton);
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> buy = new ArrayList<String>();
                for(int i = 0; i < buyStatsListItems.size(); i++){
                    BuyStatsListItem buyStatsListItem = buyStatsListItems.get(i);
                        String addStat = buyStatsListItem.getStatLabel() + ": " + buyStatsListItem.getPlusAmount();
                        buy.add(addStat.toLowerCase());


                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(BuyAgainst.this, R.style.alertDialogCustom);
                dialog.setTitle("You've Selected: " + buy.get(0) + ", " +
                        buy.get(1)+ ", " + buy.get(2)+ ", " + buy.get(3)+ ", " +
                        buy.get(4)+ ", " + buy.get(5)+ " and " + buy.get(6) + ".")
                        .setMessage("Any remaining funds are non-transferable. " +
                                "Please select 'OK' to continue.")

                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //adds some new extras to the main bundle
                                Intent intent = new Intent(BuyAgainst.this, Results.class);

                                for (int i = 0; i < buyStatsListItems.size(); i++){
                                    BuyStatsListItem buyStatsListItem = buyStatsListItems.get(i);

                                    mExtras.putString(buyStatsListItem.getStatLabel(), buyStatsListItem.getPlusAmount()+"");
                                    mExtras.putLong("BUYING_ENDS", mBuyingEnds);
                                }
                                intent.putExtras(mExtras);

                                startActivity(intent);                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });


                dialog.show();


            }
        });


        dynamicFunds = (TextView) findViewById(R.id.dynamicFundsTextView);



        DecimalFormat formatter = new DecimalFormat("$#,###");
        dynamicFunds.setText(formatter.format(mSelectedPlayer.getCapDouble()));


        expListView =  (ExpandableListView) findViewById(R.id.expandableListView);

        prepareListData();

        listAdapter = new BuyAgainstAdapter(this, buyStatsListItems, buyStatSubListItem, mSelectedPlayer.getCapDouble());

        expListView.setAdapter(listAdapter);

    }

    private void prepareListData() {
        buyStatsListItems = new ArrayList<>();
        buyStatSubListItem = new HashMap<BuyStatsListItem, BuyStatsSubListItem>();

        BuyStatsListItem buyPoints = new BuyStatsListItem();
        buyPoints.setPlayerAvg(mSelectedPlayer.getPtsAvg());
        buyPoints.setOppAvg(mOpponent.getPtsAvg());
        buyPoints.setPrice(mPrices.getPts());
        buyPoints.setStatLabel("Pts");
        buyPoints.setPlusAmount(0);

        buyStatsListItems.add(buyPoints);

        BuyStatsListItem buyRebounds = new BuyStatsListItem();
        buyRebounds.setPlayerAvg(mSelectedPlayer.getRebAvg());
        buyRebounds.setOppAvg(mOpponent.getRebAvg());
        buyRebounds.setPrice(mPrices.getReb());
        buyRebounds.setStatLabel("Reb");
        buyRebounds.setPlusAmount(0);

        buyStatsListItems.add(buyRebounds);

        BuyStatsListItem buyAssists = new BuyStatsListItem();
        buyAssists.setPlayerAvg(mSelectedPlayer.getAstAvg());
        buyAssists.setOppAvg(mOpponent.getAstAvg());
        buyAssists.setPrice(mPrices.getAst());
        buyAssists.setStatLabel("Ast");
        buyAssists.setPlusAmount(0);
        buyStatsListItems.add(buyAssists);

        BuyStatsListItem buySteals = new BuyStatsListItem();
        buySteals.setPlayerAvg(mSelectedPlayer.getStlAvg());
        buySteals.setOppAvg(mOpponent.getStlAvg());
        buySteals.setPrice(mPrices.getStl());
        buySteals.setStatLabel("Stl");
        buySteals.setPlusAmount(0);
        buyStatsListItems.add(buySteals);

        BuyStatsListItem buyBlocks = new BuyStatsListItem();
        buyBlocks.setPlayerAvg(mSelectedPlayer.getBlkAvg());
        buyBlocks.setOppAvg(mOpponent.getBlkAvg());
        buyBlocks.setPrice(mPrices.getBlk());
        buyBlocks.setStatLabel("Blk");
        buyBlocks.setPlusAmount(0);
        buyStatsListItems.add(buyBlocks);

        BuyStatsListItem buyThrees = new BuyStatsListItem();
        buyThrees.setPlayerAvg(mSelectedPlayer.get_3PtAvg());
        buyThrees.setOppAvg(mOpponent.get_3PtAvg());
        buyThrees.setPrice(mPrices.get_3pt());
        buyThrees.setStatLabel("3Pt");
        buyThrees.setPlusAmount(0);

        buyStatsListItems.add(buyThrees);

        BuyStatsListItem buyTo = new BuyStatsListItem();
        buyTo.setPlayerAvg(mSelectedPlayer.getTOAvg());
        buyTo.setOppAvg(mOpponent.getTOAvg());
        buyTo.setPrice(mPrices.getTo());
        buyTo.setStatLabel("-TO");
        buyTo.setPlusAmount(0);
        buyStatsListItems.add(buyTo);

        BuyStatsSubListItem pointsSub = new BuyStatsSubListItem();
        pointsSub.setStatSubLabel("Buy Points");

        BuyStatsSubListItem rebsSub = new BuyStatsSubListItem();
        rebsSub.setStatSubLabel("Buy Rebs");

        BuyStatsSubListItem stlsSub = new BuyStatsSubListItem();
        stlsSub.setStatSubLabel("Buy Steals");

        BuyStatsSubListItem assistsSub = new BuyStatsSubListItem();
        assistsSub.setStatSubLabel("Buy Asts");

        BuyStatsSubListItem blocksSub = new BuyStatsSubListItem();
        blocksSub.setStatSubLabel("Buy Blks");

        BuyStatsSubListItem threesSub = new BuyStatsSubListItem();
        threesSub.setStatSubLabel("Buy 3s");

        BuyStatsSubListItem buyTos = new BuyStatsSubListItem();
        buyTos.setStatSubLabel("Subtract TOs");

        buyStatSubListItem.put(buyStatsListItems.get(0), pointsSub);
        buyStatSubListItem.put(buyStatsListItems.get(1), rebsSub);
        buyStatSubListItem.put(buyStatsListItems.get(2), assistsSub);
        buyStatSubListItem.put(buyStatsListItems.get(3), stlsSub);
        buyStatSubListItem.put(buyStatsListItems.get(4), blocksSub);
        buyStatSubListItem.put(buyStatsListItems.get(5), threesSub);
        buyStatSubListItem.put(buyStatsListItems.get(6), buyTos);

    }

    private void setBuyCountdown(long i) {
        final TextView buyingEndsTextView = (TextView) findViewById(R.id.buyAgainstBuyEnding);

        new CountDownTimer(i, 1000) {

            public void onTick(long millisUntilFinished) {

                long durationSeconds = millisUntilFinished / 1000;
                mBuyingEnds = millisUntilFinished;

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

    public String getPlayerNameAbbrev(String playerName){
        String parseBySpaces = "[ ]+";
        String parseSelectedPlayerName[] = playerName.split(parseBySpaces);
        String playerLastName = parseSelectedPlayerName[1];
        if(playerLastName.length() > 5) {
            String playerLastAbrev = playerLastName.substring(0, 4) + ".";
            return playerLastAbrev;
        }else{
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

}
