package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Model.StandingsListItem;
import com.example.louissankey.darkhorselayouts.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.List;

/**
 * Created by louissankey on 5/24/16.
 */
public class StandingsAdapter extends BaseAdapter {
    private Context mContext;
    private List<StandingsListItem> mPlayers;

    public StandingsAdapter(Context context, List<StandingsListItem> players) {
        mContext = context;
        mPlayers = players;
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
        StandingsListItem player = mPlayers.get(position);



            LayoutInflater inflater = LayoutInflater.from(mContext);
            viewHolder = new ViewHolder();

            switch (player.getDisplayType()){
                case 0:
                case 1:

                    convertView = inflater.inflate(R.layout.item_standings, parent, false);
                    viewHolder.mPlayerName = (TextView) convertView.findViewById(R.id.standingsPlayerName);
                    viewHolder.mUsername = (TextView) convertView.findViewById(R.id.standingsUsername);
                    viewHolder.mFantasyPoints = (TextView) convertView.findViewById(R.id.fantasyPtsAmt);
                    viewHolder.mPlayerName.setText(player.getPlayerName());
                    viewHolder.mUsername.setText(player.getUsername());
                    viewHolder.mFantasyPoints.setText("Fantasy Points: 22");

                    if(!player.isDraftedOpponent()){
                        viewHolder.mUsername.setTextColor(ContextCompat.getColor(mContext, R.color.user));
                    }


                    break;
                case 3:
                    convertView = inflater.inflate(R.layout.item_lobby_accepting, parent, false);
                    ShimmerTextView accepting = (ShimmerTextView) convertView.findViewById(R.id.acceptingIndicator);
                    accepting.setText("" + (position + 1) + "/" + mPlayers.size() + ": Accepting . . .");
                    accepting.setTextSize(22);

                    Shimmer shimmer = new Shimmer();
                    shimmer.start(accepting);
                    break;
            }





        return convertView;
    }

    private static class ViewHolder {
        TextView mPlayerName;
        TextView mUsername;
        TextView mFantasyPoints;


    }
}