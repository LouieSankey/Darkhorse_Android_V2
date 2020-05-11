package com.example.louissankey.darkhorselayouts.Adapter;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.louissankey.darkhorselayouts.Model.BreakDownCategory;
import com.example.louissankey.darkhorselayouts.Model.BreakDownSubItem;
import com.example.louissankey.darkhorselayouts.Model.Player;
import com.example.louissankey.darkhorselayouts.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by louissankey on 6/12/16.
 */
public class BreakDownAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<BreakDownCategory> mBreakdowns;
    private Map<BreakDownCategory, List<BreakDownSubItem>> mCategoryBreakDownMap;


    public BreakDownAdapter(Context context, List<BreakDownCategory> breakDowns,
                          HashMap<BreakDownCategory, List<BreakDownSubItem>> categoryBreakDownMap) {
        mContext = context;
        mBreakdowns = breakDowns;
        mCategoryBreakDownMap = categoryBreakDownMap;


    }

    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        final BreakDownCategory breakdown = mBreakdowns.get(groupPosition);

        LayoutInflater infalInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.item_break_down_category_sub, null);



        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mCategoryBreakDownMap.get(mBreakdowns.get(groupPosition)).size();

    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mCategoryBreakDownMap.get(mBreakdowns.get(groupPosition)).get(childPosition);

    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mBreakdowns.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return mBreakdowns.size();
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
        final BreakDownCategory breakDownItem = (BreakDownCategory) getGroup(groupPosition);



        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.item_break_down_category, null);
            groupViewHolder = new GroupViewHolder();
            convertView.setTag(groupViewHolder);
        } else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.category = (TextView) convertView.findViewById(R.id.breakDownCategory);
        groupViewHolder.category.setText(breakDownItem.getCategoryLabel());

        groupViewHolder.breakdownExpandIndicator = (ImageView) convertView.findViewById(R.id.breakdownExpandIndicator);



        if (isExpanded) {
            groupViewHolder.breakdownExpandIndicator.setImageResource(R.drawable.w_dash);
        } else {
            groupViewHolder.breakdownExpandIndicator.setImageResource(R.drawable.w_down);
        }

        return convertView;
    }

    public class GroupViewHolder {

        TextView category;
        ImageView breakdownExpandIndicator;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}



