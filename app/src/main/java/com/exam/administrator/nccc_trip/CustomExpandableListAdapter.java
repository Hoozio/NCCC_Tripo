package com.exam.administrator.nccc_trip;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {
    public String array[];

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    //차일드 뷰를 반환
    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    //차일드뷰의 ID반환
    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    //차일드의 각각의 row
    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        String expandedListText = (String) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.child_listview, null);
        }

        array=expandedListText.split("\\/");

        Log.i("◀◀◀◀◀","◀◀◀"+array[0]);
        Log.i("◀◀◀◀◀","◀◀◀"+array[1]);
        Log.i("◀◀◀◀◀","◀◀◀"+array[2]);


        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.childtexttitle);
        expandedListTextView.setText(array[0]);
        TextView expandedListTextView2 = (TextView) convertView.findViewById(R.id.childtextaddress);
        expandedListTextView2.setText(array[2]);
        TextView expandedListTextView3 = (TextView) convertView.findViewById(R.id.childtextcall);
        expandedListTextView3.setText(array[1]);



        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }
    //그룹 포지션 반환
    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    //그룹 사이즈를 반환
    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    //그룹 ID를 반환
    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    //그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }


        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);

        listTitleTextView.setTypeface(null, Typeface.BOLD);


        listTitleTextView.setText(listTitle);



        Log.i("convertiView :::", ""+convertView);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}