package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Model.MatchResultsItem;
import com.example.louissankey.darkhorselayouts.R;

import java.util.List;

/**
 * Created by louissankey on 4/29/16.
 */


public class ResultsAdapter extends BaseAdapter {
    private Context mContext;
    private List<MatchResultsItem> mMatchResultsItem;

    public ResultsAdapter(Context context, List<MatchResultsItem> items) {
        mContext = context;
        mMatchResultsItem = items;
    }

    @Override
    public int getCount() {
        return mMatchResultsItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mMatchResultsItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_results, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.statCategory = (TextView) convertView.findViewById(R.id.matchCategory);
            viewHolder.playerBox = (TextView) convertView.findViewById(R.id.matchPlayerBox);
            viewHolder.playerBuy = (TextView) convertView.findViewById(R.id.matchPlayerBuy);
            viewHolder.playerTotal = (TextView) convertView.findViewById(R.id.matchPlayerTotal);
            viewHolder.playerName = (TextView) convertView.findViewById(R.id.matchPlayerName);
            viewHolder.playerWin = (TextView) convertView.findViewById(R.id.matchPlayerWin);
            viewHolder.opponentBox = (TextView) convertView.findViewById(R.id.matchOppBox);
            viewHolder.opponentBuy = (TextView) convertView.findViewById(R.id.matchOppBuy);
            viewHolder.opponentTotal = (TextView) convertView.findViewById(R.id.matchOppTotal);
            viewHolder.opponentName = (TextView) convertView.findViewById(R.id.matchOppName);
            viewHolder.opponentWin = (TextView) convertView.findViewById(R.id.matchOppWin);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        MatchResultsItem matchResultsItem = mMatchResultsItem.get(position);

        viewHolder.statCategory.setText(matchResultsItem.getStatCategory());
        viewHolder.playerBox.setText(matchResultsItem.getPlayerBox());
        viewHolder.playerBuy.setText(matchResultsItem.getPlayerBuy());
        viewHolder.playerTotal.setText(matchResultsItem.getPlayerTotal());
        viewHolder.playerName.setText(matchResultsItem.getPlayerName());
        viewHolder.playerWin.setText(matchResultsItem.getPlayerWin());
        viewHolder.opponentBox.setText(matchResultsItem.getOpponentBox());
        viewHolder.opponentBuy.setText(matchResultsItem.getOpponentBuy());
        viewHolder.opponentTotal.setText(matchResultsItem.getOpponentTotal());
        viewHolder.opponentName.setText(matchResultsItem.getOpponentName());
        viewHolder.opponentWin.setText(matchResultsItem.getOpponentWin());

        return convertView;
    }


    private static class ViewHolder {

        TextView statCategory;
        TextView playerName;
        TextView opponentName;
        TextView playerBox;
        TextView playerBuy;
        TextView playerTotal;
        TextView opponentBox;
        TextView opponentBuy;
        TextView opponentTotal;
        TextView playerWin;
        TextView opponentWin;

    }
}
