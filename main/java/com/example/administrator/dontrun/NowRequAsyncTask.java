package com.example.administrator.dontrun;

import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Administrator on 2015-12-21.
 */
public class NowRequAsyncTask {
    DataClass data = new DataClass();
    String datetime_format = "/1d/1min/time/";
    String url_cal = "https://api.fitbit.com/1/user/3TCF7H/activities/calories/date/";
    String url_step = "https://api.fitbit.com/1/user/3TCF7H/activities/steps/date/";
    String url_distance = "https://api.fitbit.com/1/user/3TCF7H/activities/distance/date/";
    String time, date;
    Double distance_value, calories_value;
    int steps_value, mets_value;
    TextView testtv;
    private static final int SEND_THREAD_INFOMATION = 0;
    private static final int SEND_THREAD_STOP_MESSAGE = 1;
 //   public NowActivity.SendMassgeHandler mMainHandler;

    public void executeRequest(String time, String date) {
        this.time = time;
        this.date = date;
//        Date today = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
//        date = format.format(today);
        new NowAsyncTask().execute(url_cal);
    }

    public class NowAsyncTask extends AsyncTask<String, String, String> {
        String httpStateError = "";

        @Override
        protected String doInBackground(String... params) {
            BufferedReader fromServer = null;
            HttpsURLConnection connection = null;
            try {
                String urlll = params[0] + date + datetime_format + time + "/" +time + ".json";
                Log.d("response", urlll);
                URL targetURL = new URL(urlll);
                connection = (HttpsURLConnection) targetURL.openConnection();
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", data.getAccessToken());

                connection.setDoInput(true);

                int responseCode = connection.getResponseCode();
                if (responseCode >= 200 && responseCode < 300) {
                    Log.d("response", "responsecode : "+responseCode);
                    StringBuilder jsonBuf = new StringBuilder();
                    fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    String jsonLine = null;
                    while ((jsonLine = fromServer.readLine()) != null) {
                        jsonBuf.append(jsonLine);
                    }
                    JSONParseFun(jsonBuf, params[0]);

                }else{
                    Log.d("response", "네트워크오류발생 : " + responseCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase(url_cal)) {
                new NowAsyncTask().execute(url_step);
            }else if(s.equalsIgnoreCase(url_step)){
                new NowAsyncTask().execute(url_distance);
            }else if(s.equalsIgnoreCase(url_distance)){
                int returnDecibel = DecibelCalFun();
                // 메시지 얻어오기
                Message msg = NowActivity.mMainHandler.obtainMessage();
                // 메시지 ID 설정
                msg.what = SEND_THREAD_INFOMATION;
                // 메시지 정보 설정 (int 형식)
               msg.arg1 = Integer.valueOf(returnDecibel);
                // 메시지 정보 설정2 (int 형식)
                msg.arg2 = returnDecibel/10;
                NowActivity.mMainHandler.sendMessage(msg);
              }
        }

        public int DecibelCalFun(){
            int decibel;
            Log.d("response", "cal: "+calories_value + " steps: "+steps_value+" mets: "+mets_value+" distance : " + distance_value);
            double result = calories_value/(data.getWeight() * 2.20462);
            result *= 1000;
            decibel = (int)result;
            decibel += mets_value;
            decibel /= 2;
            decibel += 15;
            if(decibel >= 100){
                decibel = 100;
            }
            return decibel;
        }

        public void JSONParseFun(StringBuilder jsonBuf, String casestr) {
         //   time = "15:08";
            try {
                JSONObject rootJSON = new JSONObject(jsonBuf.toString());
                Log.d("response", "root : " + rootJSON.toString());

                if (casestr.equalsIgnoreCase(url_cal)) {
                    JSONArray activities1 = rootJSON.getJSONArray("activities-calories");
                    JSONObject obj1 = activities1.getJSONObject(0);
                    calories_value = obj1.getDouble("value");

                    JSONObject activities_data = rootJSON.getJSONObject("activities-calories-intraday");
                    JSONArray dataset = activities_data.getJSONArray("dataset");
                    JSONObject now_data = dataset.getJSONObject(0);
                    mets_value = now_data.getInt("mets");

                } else if (casestr.equalsIgnoreCase(url_step)) {
                    JSONArray activities1 = rootJSON.getJSONArray("activities-steps");
                    JSONObject obj2 = activities1.getJSONObject(0);
                    steps_value = obj2.getInt("value");

                } else if (casestr.equalsIgnoreCase(url_distance)) {
                    JSONArray activities1 = rootJSON.getJSONArray("activities-distance");
                    JSONObject obj3 = activities1.getJSONObject(0);
                    distance_value = obj3.getDouble("value");
                }
            } catch (Exception e) {
                Log.e("parsingJSON 오류", e.toString());
            }
        }
    }


}
