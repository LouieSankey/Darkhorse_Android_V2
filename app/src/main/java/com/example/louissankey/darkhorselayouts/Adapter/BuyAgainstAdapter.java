package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Activity.BuyAgainst;
import com.example.louissankey.darkhorselayouts.Model.BuyStatsListItem;
import com.example.louissankey.darkhorselayouts.Model.BuyStatsSubListItem;
import com.example.louissankey.darkhorselayouts.Model.StatPrices;
import com.example.louissankey.darkhorselayouts.R;
import com.hanks.htextview.HTextViewType;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

/**
 * Created by louissankey on 5/1/16.
 */
public class BuyAgainstAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<BuyStatsListItem> mListItems;
    private HashMap<BuyStatsListItem, BuyStatsSubListItem> mListItemSub;
    private double mDynamicFundsAmount;
    private double mBeginningMatchFunds;


    public interface OnDataChangeListener{
        public void onDataChanged(int size);
    }
    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public BuyAgainstAdapter(Context context, List<BuyStatsListItem> listItems,
                             HashMap<BuyStatsListItem, BuyStatsSubListItem> listItemSub, double beginningMatchFunds) {
        mContext = context;
        mListItems = listItems;
        mListItemSub = listItemSub;
        mBeginningMatchFunds = beginningMatchFunds;


    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mListItemSub.get(mListItems.get(groupPosition));
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        final BuyStatsSubListItem buyStatsSubListItem = (BuyStatsSubListItem) getChild(groupPosition, childPosition);
        final BuyStatsListItem buyStatsListItem = (BuyStatsListItem) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_buy_stats_sub, null);

        }

        TextView statLabel = (TextView) convertView.findViewById(R.id.buyStatsSubLabel);
        statLabel.setText(buyStatsSubListItem.getStatSubLabel());

        Button subtractButton = (Button) convertView.findViewById(R.id.subtractButton);
        Button plusButton = (Button) convertView.findViewById(R.id.plusButton);
        TextView toAddendum = (TextView) convertView.findViewById(R.id.tosAddendum);


        if(buyStatsSubListItem.getStatSubLabel().equals("Buy Tos")){
            toAddendum.setVisibility(View.VISIBLE);
        }else{
            toAddendum.setVisibility(View.GONE);

        }

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( buyStatsListItem.getPlusAmount() > 0 ) {
                    int minus = buyStatsListItem.getPlusAmount() - 1;
                    buyStatsListItem.setPlusAmount(minus);
                    notifyDataSetChanged();


                }
                }


        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(mDynamicFundsAmount >= buyStatsListItem.getPrice()) {
                int plus = buyStatsListItem.getPlusAmount() + 1;
                buyStatsListItem.setPlusAmount(plus);
                notifyDataSetChanged();


            }


            }
        });

        setRemainingFundsTextView();



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return mListItems.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mListItems.size();
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final BuyStatsListItem listItem = (BuyStatsListItem) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_buy_stats, null);
        }

        TextView statLabel = (TextView) convertView.findViewById(R.id.statLabelTextView);
        TextView avg = (TextView) convertView.findViewById(R.id.playerAverageTextView);
        TextView oppAvg = (TextView) convertView.findViewById(R.id.opponentAverageTextView);
        TextView price = (TextView) convertView.findViewById(R.id.priceTextView);
        TextView plus = (TextView) convertView.findViewById(R.id.plusTextView);
        ImageView expand = (ImageView) convertView.findViewById(R.id.expandImageView);


            statLabel.setText(listItem.getStatLabel());
            avg.setText(listItem.getPlayerAvg());
            oppAvg.setText(listItem.getOppAvg());


            if(listItem.getPlusAmount() > 0) {
                if(listItem.getStatLabel().equals("-TO")){
                    plus.setText("-  " + listItem.getPlusAmount());
                }else {
                    plus.setText("+ " + listItem.getPlusAmount());
                }

            }else{
                plus.setText("");
            }
        DecimalFormat formatter = new DecimalFormat("$#,###");
            price.setText(formatter.format(listItem.getPrice()));



        if (isExpanded) {
            expand.setImageResource(R.drawable.w_dash);

        } else {
            expand.setImageResource(R.drawable.w_down);
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setRemainingFundsTextView(){


        //TODO: there must be a better way to do this!!! Needed for updating "funds remaining" in the activity

        DecimalFormat formatter = new DecimalFormat("$#,###");
        Double total = (mListItems.get(0).getPlusAmount()) * mListItems.get(0).getPrice() +
                (mListItems.get(1).getPlusAmount()) * mListItems.get(1).getPrice() +
                (mListItems.get(2).getPlusAmount()) * mListItems.get(2).getPrice() +
                (mListItems.get(3).getPlusAmount()) * mListItems.get(3).getPrice() +
                (mListItems.get(4).getPlusAmount()) * mListItems.get(4).getPrice() +
                (mListItems.get(5).getPlusAmount()) * mListItems.get(5).getPrice() +
                (mListItems.get(6).getPlusAmount()) * mListItems.get(6).getPrice();

        mDynamicFundsAmount = mBeginningMatchFunds - total;
        String funds = formatter.format(mDynamicFundsAmount);

        //TODO: static TextView may be bad practice




        BuyAgainst.dynamicFunds.setText(funds);


    }




}