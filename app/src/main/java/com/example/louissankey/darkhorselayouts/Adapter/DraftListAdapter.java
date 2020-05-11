package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.R;
import com.romainpiel.shimmer.Shimmer;

import java.util.List;

/**
 * Created by louissankey on 4/29/16.
 */


public class DraftListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Player> mPlayers;

    public DraftListAdapter(Context context, List<Player> items) {
        mContext = context;
        mPlayers = items;
    }

    @Override
    public int getCount() {
        return mPlayers.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Shimmer shimmer = new Shimmer();

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_draft_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.playerName = (TextView) convertView.findViewById(R.id.playerNameTextView);
            viewHolder.playerVersus = (TextView) convertView.findViewById(R.id.playerVersusTextView);
            viewHolder.playerFundsPerMatch = (TextView) convertView.findViewById(R.id.playerFundsPerMatchTextView);
            viewHolder.playerGamePrice = (TextView) convertView.findViewById(R.id.playerGamePriceTextView);
            viewHolder.usernameTextView = (com.romainpiel.shimmer.ShimmerTextView) convertView.findViewById(R.id.usernameTextView);
            //shimmer.start(viewHolder.usernameTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Player player = mPlayers.get(position);
        viewHolder.playerName.setText(player.getPlayerName());
        viewHolder.playerVersus.setText("HTM @ AWT");
        viewHolder.playerGamePrice.setText(player.getPrice());
        viewHolder.playerFundsPerMatch.setText(player.getCapString());
        viewHolder.usernameTextView.setText(player.getUsername());

        return convertView;
    }


    private static class ViewHolder {
        TextView playerName;
        TextView playerVersus;
        TextView playerGamePrice;
        TextView playerFundsPerMatch;
        com.romainpiel.shimmer.ShimmerTextView usernameTextView;
    }
}
