package com.exm.roper.whatweeat;

import android.graphics.Bitmap;

/**
 * Created by Roper on 2018/4/22.
 */

public class Fragement_two_UserPreferSeting_bean {
    private String dish_name;
    private Bitmap dish_draw;

    public Fragement_two_UserPreferSeting_bean(String dish_name,Bitmap dish_draw) {
        this.dish_name = dish_name;
        this.dish_draw = dish_draw;
    }
    public Bitmap getDish_draw() {
        return dish_draw;
    }

    public void setDish_draw(Bitmap dish_draw) {
        this.dish_draw = dish_draw;
    }


    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }


}
