package com.exm.roper.whatweeat;

/**
 * Created by Roper on 2018/6/10.
 */

public class Deatial_bean {

    private String dish_name;
    private String dish_price;

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDish_price() {
        return dish_price;
    }

    public void setDish_price(String dish_price) {
        this.dish_price = dish_price;
    }

    public Deatial_bean(String dish_name, String dish_price) {
        this.dish_name = dish_name;
        this.dish_price = dish_price;
    }

}
