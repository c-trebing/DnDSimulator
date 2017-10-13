package com.powerrangers.todo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    public final static String EXTRA_MESSAGE = "";

    private Context _context;
    private List<Date> _listHeaders;
    private HashMap<Date, List<Task>> _listChildren;

    public MyExpandableListAdapter(Context context, List<Date> listHeaders,
            HashMap<Date, List<Task>> listChildren) {
        this._context = context;
        this._listHeaders = listHeaders;
        this._listChildren = listChildren;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listChildren.get(this._listHeaders.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
            boolean isLastChild, View convertView, ViewGroup parent) {

        SimpleDateFormat taskEntryFormat = new SimpleDateFormat("hh:mm a  -  ");
        Task child = (Task) getChild(groupPosition, childPosition);
        String time = taskEntryFormat.format(child.calendar.getTime());
        String childText = time + child.name;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        txtListChild.setPadding(20,10,20,10);

        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Context context = v.getContext();
              Intent intent = new Intent(context, EditTaskActivity.class);
              TextView tv = (TextView)v;
              String message = tv.getText().toString();
              intent.putExtra(EXTRA_MESSAGE, message);
              context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listChildren.get(this._listHeaders.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        Date header = (Date) getGroup(groupPosition);
        SimpleDateFormat taskHeaderFormat = new SimpleDateFormat("EEEE, MMM d");
        String headerTitle = taskHeaderFormat.format(header);
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.list_header);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setNewItems(List<Date> listHeaders, HashMap<Date, List<Task>> listChildren) {
      this._listHeaders = listHeaders;
      this._listChildren = listChildren;
      notifyDataSetChanged();
    }
}
