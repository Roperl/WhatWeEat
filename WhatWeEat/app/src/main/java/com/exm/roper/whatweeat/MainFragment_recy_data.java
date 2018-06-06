package com.exm.roper.whatweeat;

import android.graphics.Bitmap;

/**
 * Created by Roper on 2018/4/8.
 */
public class MainFragment_recy_data {
    private String res_name;
    private String res_location;
    private Bitmap img;

    public MainFragment_recy_data() {
    }

    public MainFragment_recy_data(String res_name, String res_location, Bitmap img) {
        this.res_name = res_name;
        this.res_location = res_location;
        this.img = img;
    }

    public String getRes_name() {
        return res_name;
    }

    public void setRes_name(String res_name) {
        this.res_name = res_name;
    }

    public String getRes_location() {
        return res_location;
    }

    public void setRes_location(String res_location) {
        this.res_location = res_location;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}

