package com.example.louissankey.darkhorselayouts.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Adapter.BreakDownAdapter;
import com.example.louissankey.darkhorselayouts.Model.BreakDownCategory;
import com.example.louissankey.darkhorselayouts.Model.BreakDownSubItem;
import com.example.louissankey.darkhorselayouts.Model.StandingsListItem;
import com.example.louissankey.darkhorselayouts.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BreakDown extends AppCompatActivity {
    private ExpandableListView breakDownList;

    private List<BreakDownCategory> mBreakdowns;
    private HashMap<BreakDownCategory, List<BreakDownSubItem>> mCategoryBreakDownMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_down);

        StandingsListItem standingsListItem = (StandingsListItem) getIntent().getExtras().get(getString(R.string.STANDINGS_ITEM));

        TextView username = (TextView) findViewById(R.id.breakdownUsername);
        TextView playerName = (TextView) findViewById(R.id.breakdownPlayerName);
        TextView position = (TextView) findViewById(R.id.breakdownPosition);
        TextView fantasyPoints = (TextView) findViewById(R.id.breakdownFantasyPoints);
        if(standingsListItem.getDisplayType() == 0){
            username.setTextColor(Color.parseColor("#76c6f8"));
        }


        username.setText(standingsListItem.getUsername());
        playerName.setText(standingsListItem.getPlayerName());
        position.setText("1st");
        fantasyPoints.setText(standingsListItem.getFantasyPoints());














        prepareData();


        breakDownList = (ExpandableListView) findViewById(R.id.breakDownList);
        BreakDownAdapter adapter = new BreakDownAdapter(this, mBreakdowns, mCategoryBreakDownMap);
        breakDownList.setAdapter(adapter);


    }

    public void prepareData(){

        mBreakdowns = new ArrayList<>();
        mCategoryBreakDownMap = new HashMap<>();


        BreakDownCategory points = new BreakDownCategory();
        points.setCategoryLabel("0 Points");
        mBreakdowns.add(points);

            ArrayList<BreakDownSubItem> pointsSub = new ArrayList<>();
            BreakDownSubItem ptsOpp1 = new BreakDownSubItem();
            pointsSub.add(ptsOpp1);
            BreakDownSubItem ptsOpp2 = new BreakDownSubItem();
            pointsSub.add(ptsOpp2);



        BreakDownCategory rebs = new BreakDownCategory();
        rebs.setCategoryLabel("0 Rebounds");
        mBreakdowns.add(rebs);

            ArrayList<BreakDownSubItem> rebSub = new ArrayList<>();
            BreakDownSubItem rebOpp1 = new BreakDownSubItem();
            rebSub.add(rebOpp1);
            BreakDownSubItem rebOpp2 = new BreakDownSubItem();
            rebSub.add(rebOpp2);



        BreakDownCategory asts = new BreakDownCategory();
        asts.setCategoryLabel("0 Assists");
        mBreakdowns.add(asts);

            ArrayList<BreakDownSubItem> astSub = new ArrayList<>();
            BreakDownSubItem astOpp1 = new BreakDownSubItem();
            astSub.add(astOpp1);
            BreakDownSubItem astOpp2 = new BreakDownSubItem();
            astSub.add(astOpp2);



        BreakDownCategory stls = new BreakDownCategory();
        stls.setCategoryLabel("0 Steals");
        mBreakdowns.add(stls);

        ArrayList<BreakDownSubItem> stlSub = new ArrayList<>();
        BreakDownSubItem stlOpp1 = new BreakDownSubItem();
        stlSub.add(stlOpp1);
        BreakDownSubItem stlOpp2 = new BreakDownSubItem();
        stlSub.add(stlOpp2);


        BreakDownCategory blk = new BreakDownCategory();
        blk.setCategoryLabel("0 Blocks");
        mBreakdowns.add(blk);

        ArrayList<BreakDownSubItem> blkSub = new ArrayList<>();
        BreakDownSubItem blkOpp1 = new BreakDownSubItem();
        blkSub.add(blkOpp1);
        BreakDownSubItem blkOpp2 = new BreakDownSubItem();
        blkSub.add(blkOpp2);


        BreakDownCategory to = new BreakDownCategory();
        to.setCategoryLabel("0 Turnovers");
        mBreakdowns.add(to);

        ArrayList<BreakDownSubItem> toSub = new ArrayList<>();
        BreakDownSubItem toOpp1 = new BreakDownSubItem();
        toSub.add(toOpp1);
        BreakDownSubItem toOpp2 = new BreakDownSubItem();
        toSub.add(toOpp2);

        BreakDownCategory _3Pt = new BreakDownCategory();
        _3Pt.setCategoryLabel("0 3Pts");
        mBreakdowns.add(_3Pt);

        ArrayList<BreakDownSubItem> _3PtSub = new ArrayList<>();
        BreakDownSubItem _3PtOpp1 = new BreakDownSubItem();
        _3PtSub.add(_3PtOpp1);
        BreakDownSubItem _3PtOpp2 = new BreakDownSubItem();
        _3PtSub.add(_3PtOpp2);



        mCategoryBreakDownMap.put(mBreakdowns.get(0), pointsSub);
        mCategoryBreakDownMap.put(mBreakdowns.get(1), rebSub);
        mCategoryBreakDownMap.put(mBreakdowns.get(2), astSub);
        mCategoryBreakDownMap.put(mBreakdowns.get(3), stlSub);
        mCategoryBreakDownMap.put(mBreakdowns.get(4), blkSub);
        mCategoryBreakDownMap.put(mBreakdowns.get(5), toSub);
        mCategoryBreakDownMap.put(mBreakdowns.get(6), _3PtSub);


    }
}
