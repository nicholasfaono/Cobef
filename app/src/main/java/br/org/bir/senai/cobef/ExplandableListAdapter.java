package br.org.bir.senai.cobef;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class ExplandableListAdapter extends BaseExpandableListAdapter {


    private Context mContext;
    private List<String> mGroupList;
    private HashMap<String, List<String>> mChildList;

    public ExplandableListAdapter(Context context,
                                  List<String> groupList,
                                  HashMap<String, List<String>> childList){
        mContext = context;
        mGroupList = groupList;
        mChildList = childList;
    }
    @Override
    public int getGroupCount() {
        return mGroupList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (mChildList.containsKey(mGroupList.get(groupPosition))) {
            return mChildList.get(mGroupList.get(groupPosition)).size();
        }

        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (mChildList.containsKey(mGroupList.get(groupPosition))) {
            return mChildList.get(mGroupList.get(groupPosition)).get(childPosition);
        }

        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String title = (String)getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater)mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView textView = (TextView)convertView.findViewById(R.id.text1);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String text = (String) getChild(groupPosition, childPosition);

        if (text == null) {
            return null;
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView textView = (TextView) convertView.findViewById(R.id.text1);
        textView.setText(text);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
