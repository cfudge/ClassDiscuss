package com.example.cmput401.classdiscuss;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Nancy on 4/6/2015.
 */
//http://stackoverflow.com/questions/9824074/android-expandablelistview-looking-for-a-tutorial
//https://www.youtube.com/watch?v=_h94Kqyc-Ag&feature=iv&src_vid=BkazaAeeW1Q&annotation_id=annotation_1141555157
public class MutualClassAdapter extends BaseExpandableListAdapter {

    Context context;
    ArrayList<String> courses;
    Connections myConnections;

    CheckBox checkBox;

    ArrayList<OtherUserMapInfo> listItems;


    boolean [] itemChecked;

    public MutualClassAdapter (Context context, ArrayList<OtherUserMapInfo> listItems, ArrayList<String> courses){
        this.context = context;
        this.courses = courses;
        this.listItems = listItems;

        myConnections = Connections.getInstance();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {

        return listItems.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return courses.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        return listItems.get(groupPosition).getUsername();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {


        return courses.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View customView = inflater.inflate(R.layout.popup_list,parent,false);

      /*  ViewHolder holder;


        holder = new ViewHolder();*/

        final String users = getGroup(groupPosition).toString();


        final TextView listText = (TextView) customView.findViewById(R.id.popupText);
        listText.setText(users);

        checkBox = (CheckBox) customView.findViewById(R.id.checkBox);

       checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                itemChecked[groupPosition] = isChecked;

                if(isChecked){
                    if(myConnections.myConnections.contains(users));
                    else
                        myConnections.myConnections.add(listText.getText().toString());
                    if(!myConnections.tempConnections.contains(users))
                        myConnections.tempConnections.add(listText.getText().toString());


                }
                else
                    myConnections.tempConnections.remove(listText.getText().toString());

            }
        });
      //  holder.checkBox.setChecked(itemChecked[groupPosition]);*/
        return customView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

       // View customView = convertView;
       LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View customView = inflater.inflate(R.layout.child_layout, parent, false);
       // ViewHolder holder;

        String courses = getChild(groupPosition,childPosition).toString();


           // holder = new ViewHolder();

           // holder.mutualCourses = (TextView) customView.findViewById(R.id.list_classes);
           // holder.mutualCourses.setText("TEST");

        TextView mutualCourses = (TextView) customView.findViewById(R.id.list_classes);
        mutualCourses.setText(courses);

        return customView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    final class ViewHolder{

        CheckBox checkBox;
    }

}
