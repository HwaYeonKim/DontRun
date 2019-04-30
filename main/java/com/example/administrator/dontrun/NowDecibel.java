package com.example.administrator.dontrun;

import android.util.Log;

/**
 * Created by Administrator on 2015-12-24.
 */
public class NowDecibel {

    private int nowDecibel;
    private int nowLevel;

    public void setData(int dec, int lev){
        this.nowDecibel = dec;
        this.nowLevel = lev;
        Log.d("response", "dec: " + nowDecibel + " level: "+ nowLevel);
    }

    public int getDecibel(){
        return nowDecibel;
    }

    public int getLevel(){
        return nowLevel;
    }

}
