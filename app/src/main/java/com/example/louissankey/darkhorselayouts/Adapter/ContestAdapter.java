package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Activity.DraftList;
import com.example.louissankey.darkhorselayouts.Activity.Contests;
import com.example.louissankey.darkhorselayouts.Model.ContestItem;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.R;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by louissankey on 5/8/16.
 */
public class ContestAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<ContestItem> mContests;
    private Map<ContestItem, List<Player>> mContestOpponentMap;
    private HashMap<TextView, CountDownTimer> counters;
    private Bundle mExtras;




    public ContestAdapter(Context context, List<ContestItem> contests,
                          LinkedHashMap<ContestItem, List<Player>> contestOpponentMap, Bundle extras) {
        mContext = context;
        mContests = contests;
        mContestOpponentMap = contestOpponentMap;
        this.counters = new HashMap<TextView, CountDownTimer>();
        mExtras = extras;


    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        final Player lobbyListItemSub = (Player) getChild(groupPosition, childPosition);
        final ContestItem contest = mContests.get(groupPosition);

        LayoutInflater infalInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Shimmer shimmer = new Shimmer();


        switch (lobbyListItemSub.getDisplayType()) {

            case 1:


                convertView = infalInflater.inflate(R.layout.item_lobby_opponent, null);
                TextView playerName = (TextView) convertView.findViewById(R.id.lobbyPlayerName);
                TextView fundsRemaining = (TextView) convertView.findViewById(R.id.lobbyFundsRemaining);
                TextView priceDrafted = (TextView) convertView.findViewById(R.id.subItemPriceDrafted);
                ShimmerTextView username = (ShimmerTextView) convertView.findViewById(R.id.lobbyUsername);
                username.setText(lobbyListItemSub.getUsername());
                playerName.setText(lobbyListItemSub.getPlayerName());
                priceDrafted.setText("Game Price: " + lobbyListItemSub.getPrice());
                fundsRemaining.setText("Funds Remaining: " + lobbyListItemSub.getCapString());


                break;
            case 2:
                convertView = infalInflater.inflate(R.layout.item_lobby_header, null);
                convertView.setClickable(true);
                break;


            case 3:
                convertView = infalInflater.inflate(R.layout.item_lobby_accepting, null);
                ShimmerTextView contestentNumber = (ShimmerTextView) convertView.findViewById(R.id.acceptingIndicator);
                int contestentPosition = childPosition;
                int totalContestents = getChildrenCount(groupPosition) - 2;
                contestentNumber.setText("" + contestentPosition + "/" + totalContestents + ": Accepting . . .");
                convertView.setClickable(true);
                shimmer.start(contestentNumber);
                break;

            case 4:
                convertView = infalInflater.inflate(R.layout.item_lobby_draft_button, null);
                convertView.setClickable(true);
                Button draftButton = (Button) convertView.findViewById(R.id.lobbyDraftButton);
                draftButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<Player> listItems = (ArrayList<Player>) mContestOpponentMap.get(mContests.get(groupPosition));

                        //passes a new opponent arraylist without list elements used for header or button
                        ArrayList<Player> opponents = new ArrayList<>();
                        for (int i = 0; i < listItems.size(); i++) {
                            Player opp = listItems.get(i);
                            if (opp.getDisplayType() == 1 || opp.getDisplayType() == 3) {
                                opponents.add(opp);
                            }
                        }

                        Intent intent = new Intent(mContext, DraftList.class);
                        intent.putExtras(mExtras);
                        intent.putExtra(Contests.OPPONENTS, opponents);
                        intent.putExtra("DRAFT_TIME_REMAINING", contest.getDraftEnds());
                        intent.putExtra("ACCEPTED", contest.getAccepting());
                        mContext.startActivity(intent);
                    }
                });

                break;
            default:
                break;
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mContestOpponentMap.get(mContests.get(groupPosition)).size();

    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mContestOpponentMap.get(mContests.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mContests.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mContests.size();
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {

        final GroupViewHolder groupViewHolder;
        final ContestItem contestItem = (ContestItem) getGroup(groupPosition);



        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_lobby, null);
            groupViewHolder = new GroupViewHolder();
            convertView.setTag(groupViewHolder);


        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();

        }

        groupViewHolder.lobbyItemExpandIndicator = (ImageView) convertView.findViewById(R.id.lobbyItemExpandIndicator);
        groupViewHolder.gameType = (TextView) convertView.findViewById(R.id.lobbyGameType);
        groupViewHolder.accepting = (TextView) convertView.findViewById(R.id.lobbyAccepting);
        groupViewHolder.gamesInDraft = (TextView) convertView.findViewById(R.id.lobbyGamesInDraft);
        groupViewHolder.mContestItem = (ContestItem) getGroup(groupPosition);
        groupViewHolder.draftEnding = (TextView) convertView.findViewById(R.id.lobbyDraftEnding);

        groupViewHolder.gameType.setText(groupViewHolder.mContestItem.getGameType());
        groupViewHolder.accepting.setText(groupViewHolder.mContestItem.getAccepting());
        groupViewHolder.gamesInDraft.setText(groupViewHolder.mContestItem.getNbaGamesAmnt());



        CountDownTimer countDownTimer = counters.get(groupViewHolder.draftEnding);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(contestItem.getDraftEnds(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long durationSeconds = millisUntilFinished / 1000;
                String timeToFinish = String.format(Locale.ENGLISH, "%02d:%02d:%02d", durationSeconds / 3600,
                        (durationSeconds % 3600) / 60, (durationSeconds % 60));
                groupViewHolder.draftEnding.setText(timeToFinish);
            }

            @Override
            public void onFinish() {
                groupViewHolder.draftEnding.setText("Draft Finished");
            }
        };

        counters.put(groupViewHolder.draftEnding, countDownTimer);
        countDownTimer.start();


        if (isExpanded) {
            groupViewHolder.lobbyItemExpandIndicator.setImageResource(R.drawable.w_dash);
        } else {
            groupViewHolder.lobbyItemExpandIndicator.setImageResource(R.drawable.w_down);
        }

        return convertView;
    }



    public class GroupViewHolder {

        TextView draftEnding;
        TextView gameType;
        TextView accepting;
        TextView gamesInDraft;
        ImageView lobbyItemExpandIndicator;
        ContestItem mContestItem;




    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}



