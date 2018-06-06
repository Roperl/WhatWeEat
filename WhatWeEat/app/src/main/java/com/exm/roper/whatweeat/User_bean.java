package com.exm.roper.whatweeat;

/**
 * Created by Roper on 2018/5/15.
 */

public class User_bean {
    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getSessionid() {
        return Sessionid;
    }

    public void setSessionid(String sessionid) {
        Sessionid = sessionid;
    }

    private String Userid;
    private String Username;
    private String Sessionid;
}
