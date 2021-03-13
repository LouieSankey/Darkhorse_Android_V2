package com.example.louissankey.darkhorselayouts.Activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.louissankey.darkhorselayouts.Adapter.StandingsAdapter;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.Model.StandingsListItem;
import com.example.louissankey.darkhorselayouts.R;

import java.util.ArrayList;

public class Standings extends AppCompatActivity {


    private ArrayList<StandingsListItem> mStandingsItems;
    private Bundle mExtras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings);
        mExtras = getIntent().getExtras();
        ArrayList<Player> opponents = mExtras.getParcelableArrayList(Contests.OPPONENTS);

        mStandingsItems = new ArrayList<>();

        Player selectedPlayer = mExtras.getParcelable(DraftList.SELECTED_PLAYER);

        StandingsListItem selectedPlayerStandings = new StandingsListItem();
        selectedPlayerStandings.setPlayerName(selectedPlayer.getPlayerName());
        selectedPlayerStandings.setUsername(selectedPlayer.getUsername());
        selectedPlayerStandings.setDisplayType(0);
        selectedPlayerStandings.setFantasyPoints("0");
        mStandingsItems.add(selectedPlayerStandings);



        for(int i = 0; i < opponents.size(); i++){
            StandingsListItem item = new StandingsListItem();
            Player opponent = opponents.get(i);

            item.setPlayerName(opponent.getPlayerName());
            item.setUsername(opponent.getUsername());
            item.setDraftedOpponent(true);
            item.setDisplayType(opponent.getDisplayType());
            item.setFantasyPoints("0");
            mStandingsItems.add(item);
        }

        ListView mStandingsListView =  (ListView) findViewById(R.id.standingsListView);

        StandingsAdapter standingsAdapter = new StandingsAdapter(this, mStandingsItems);

        mStandingsListView.setAdapter(standingsAdapter);

        mStandingsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Standings.this, BreakDown.class);
                StandingsListItem standingsItem = mStandingsItems.get(position);
                intent.putExtra(getString(R.string.STANDINGS_ITEM), standingsItem);

                startActivity(intent);
            }
        });
    }




}
