package com.exm.roper.whatweeat;

/**
 * Created by Roper on 2018/4/5.
 */

public class Fragment_two_list_entity {
    private String name;
    private int imageId;

    public Fragment_two_list_entity(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getImageId() {
        return imageId;
    }
}
