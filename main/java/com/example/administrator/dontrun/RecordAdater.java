package com.example.administrator.dontrun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-12-24.
 */
public class RecordAdater extends BaseAdapter {

    Context context;
    LayoutInflater Inflater;

    TextView datetime_tv, decibel_tv;

    private int layout;

    ArrayList<RecordData> arrayList = new ArrayList<RecordData>();

    public RecordAdater(Context context, int layout,
                        ArrayList<RecordData> arrayList) {
        this.context = context;
        Inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public String getDate(int position) {
        return arrayList.get(position).getDate();
    }

    public int getAvgDecibel(){
        int sum = 0;
        for( int i=0; i<arrayList.size(); i++){
            sum += getDecibel(i);
        }
        int avg = sum/(getCount());

        return avg;
    };

    public String getTime(int position) {
        return arrayList.get(position).getTime();
    }

    public int getDecibel(int position) {
        return arrayList.get(position).getDecibel();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = Inflater.inflate(R.layout.listview_item, parent, false);

            datetime_tv = (TextView) convertView.findViewById(R.id.item_datetime);
            decibel_tv = (TextView) convertView.findViewById(R.id.item_decibel);
            datetime_tv.setText(getDate(position) + "_" + getTime(position));
            decibel_tv.setText("" + getDecibel(position));

        }
        return convertView;
    }
}
