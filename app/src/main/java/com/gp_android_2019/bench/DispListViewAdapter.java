package com.gp_android_2019.bench;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gp_android_2019.R;

import java.util.ArrayList;

public class DispListViewAdapter extends BaseAdapter {
    private ArrayList<DispListViewItem> listViewItemList = new ArrayList<DispListViewItem>();

    public DispListViewAdapter() { }

    public void addItem(String element, String content) {
        DispListViewItem item = new DispListViewItem();

        item.setElement(element);
        item.setContent(content);

        listViewItemList.add(item);
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.disp_item, parent, false);
        }

        TextView typeTextView = (TextView) convertView.findViewById(R.id.type) ;
        TextView resultTextView = (TextView) convertView.findViewById(R.id.result) ;

        DispListViewItem listViewItem = listViewItemList.get(position);

        typeTextView.setText(listViewItem.getElement());
        resultTextView.setText(listViewItem.getContent());

        return convertView;
    }
}
