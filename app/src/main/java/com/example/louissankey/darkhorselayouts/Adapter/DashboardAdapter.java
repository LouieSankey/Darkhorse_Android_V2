package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Activity.BuyAgainst;
import com.example.louissankey.darkhorselayouts.Model.BuyCountdown;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.List;

/**
 * Created by louissankey on 5/1/16.
 */
public class DashboardAdapter extends BaseAdapter {
    private Context mContext;
    private List<Player> mOpponentPlayers;
    private Player mSelectedPlayer;
    private BuyCountdown mBuyTimeRemaining;
    private StatPrices mPrices;

    //TODO: refactor mExtras and Buy Against button to outside of the adapter class if possible
    private Bundle mExtras;


    public DashboardAdapter(Context context, List<Player> opponentPlayers, Player selectedPlayer, BuyCountdown timeRemaining, StatPrices prices) {
        mContext = context;
        mOpponentPlayers = opponentPlayers;
        mSelectedPlayer = selectedPlayer;
        mBuyTimeRemaining = timeRemaining;
        mPrices = prices;


    }

    @Override
    public int getCount() {
        return mOpponentPlayers.size();
    }

    @Override
    public Object getItem(int position) {
        return mOpponentPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Player opponentPlayer = mOpponentPlayers.get(position);

            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Shimmer shimmer = new Shimmer();


        switch(opponentPlayer.getDisplayType()){
            case 1:
                convertView = infalInflater.inflate(R.layout.item_opponent_list, null);
                convertView.setClickable(true);
                ShimmerTextView username = (ShimmerTextView) convertView.findViewById(R.id.opponentUsernameTextView);
                TextView oppVersus = (TextView) convertView.findViewById(R.id.oppVersus);
                TextView draftedPlayerName = (TextView) convertView.findViewById(R.id.draftedPlayerName);

                draftedPlayerName.setText(opponentPlayer.getPlayerName());
                username.setText(opponentPlayer.getUsername());
                oppVersus.setText(opponentPlayer.getOppVersus());


                //shimmer.start(username);

                Button buyAgainstButton = (Button) convertView.findViewById(R.id.buyAgainstButton);

                if(opponentPlayer.getDraftedOpponent()){
                    buyAgainstButton.setVisibility(View.VISIBLE);
                }else{
                    buyAgainstButton.setVisibility(View.GONE);

                }

                buyAgainstButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(mContext, BuyAgainst.class);

                        intent.putExtra("SELECTED_PLAYER", mSelectedPlayer);
                        intent.putExtra("OPPONENT", opponentPlayer);
                        intent.putExtra("BUYING_ENDS", mBuyTimeRemaining.getBuyTimeRemaining());
                        intent.putExtra(mContext.getString(R.string.STAT_PRICES), mPrices);

                        intent.putExtra("LONG_TIME_IN", mBuyTimeRemaining.getTimeIn());

                        mContext.startActivity(intent);
                    }
                });


                break;
            case 3:

                convertView = infalInflater.inflate(R.layout.item_lobby_accepting, null);
                convertView.setClickable(true);

                ShimmerTextView acceptingIndicator = (ShimmerTextView) convertView.findViewById(R.id.acceptingIndicator);
                int contestentPosition = position + 1;
                int totalContestents = mOpponentPlayers.size();
                acceptingIndicator.setText("" + contestentPosition + "/" + totalContestents + ": Accepting . . .");

                shimmer.start(acceptingIndicator);


        }

        return convertView;
    }
}
