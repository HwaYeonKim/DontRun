package com.example.administrator.dontrun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015-12-21.
 */
public class TodayActivity extends Activity {
    SharedPreferences pref;
    Context context;
    ImageView tab1, tab2;
    View graphview;
    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    RecordDB recorddb = null;
    private Cursor mCursor = null;
    String date;
    DBHandler handler;
    ArrayList<RecordData> ListOfRecord;
    RecordAdater recordAdater;
    ListView list;
    TextView timestv, avgtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_today);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.activity_titlebar);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        date = pref.getString("dateDB", "");
        graphview = (View)findViewById(R.id.abc);
        graphview.setBackgroundDrawable(getResources().getDrawable(R.drawable.today_graph_bg));
        setTabAction();

        timestv = (TextView)findViewById(R.id.today_times_tv);
        avgtv = (TextView) findViewById(R.id.today_decibel_tv);

       handler = new DBHandler(this);

        ListOfRecord = new ArrayList<RecordData>();
        list = (ListView) findViewById(R.id.listview);

        try{
            ListOfRecord = handler.selectDate(date);
            recordAdater = new RecordAdater(this, android.R.layout.simple_list_item_1, ListOfRecord );
            list.setAdapter(recordAdater);
            timestv.setText(""+recordAdater.getCount());
            avgtv.setText(""+recordAdater.getAvgDecibel());
        }catch(Exception e){
            Log.d("response", "list error");
        }

  /*      mListView = (ListView) findViewById(R.id.listview);
        recorddb = new RecordDB(this);
        String query = "SELECT * FROM record_table WHERE date ='"+date+"';";
        mCursor = recorddb.getWritableDatabase().rawQuery(query,null);
        ArrayList<ListData> arData = recorddb.selectTodayData(date);

        mAdapter = new ListViewAdapter(this, arData);
        mListView.setAdapter(mAdapter);

        // TODO: 2015-12-24 데이터 불러와서 list에 저장
        ListData l1 = new ListData("Dec.24", "11:46", 87);
        ListData l2 = new ListData("Dec.24", "23:46", 90);
        ListData l3 = new ListData("Dec.24", "13:50", 82);
        mAdapter.add(l1);
        mAdapter.add(l2);
        mAdapter.add(l3);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.close();
    }

    private class ListViewAdapter extends BaseAdapter {

        private Context mContext = null;
        private ArrayList<ListData> mListData;

        public ListViewAdapter(Context mContext,ArrayList<ListData> array ){
            super();
            this.mContext = mContext;
            this.mListData = array;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_item, parent, false);

                holder.mDatetime = (TextView) convertView.findViewById(R.id.item_datetime);
                holder.mDecibel = (TextView) convertView.findViewById(R.id.item_decibel);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListData mData = mListData.get(position);

            holder.mDatetime.setText(mData.date + "_" + mData.time);
            holder.mDecibel.setText(""+mData.decibel);

            return convertView;
        }

        public void add(ListData a){
            mListData.add(a);
        }
        public void dataChange(){
            mAdapter.notifyDataSetInvalidated();
        }
    }

    private class ViewHolder{
        public TextView mDatetime;
        public TextView mDecibel;
    }

    private void setTabAction(){

        tab1 = (ImageView) findViewById(R.id.tab1_btn1);
        tab1.setClickable(true);
        tab1.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                i = new Intent(TodayActivity.this, NowActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        tab2 = (ImageView) findViewById(R.id.tab1_btn2);
        tab2.setClickable(false);
    }
}
