package com.cnit355.myles.a425project;

import android.support.annotation.NonNull;

public class Score {

    private int score;
    private String user;

    public Score(){
        score = 0;
        user = "";
    }

    public Score(int s, String u){
        score = s;
        user = u;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore() {
        return score;
    }


}
