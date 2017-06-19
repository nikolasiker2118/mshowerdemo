package com.example.kovac94.meteorshower.score;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikolan on 10/13/2016.
 */

public class Scores {
    int score;
    String nick;

    public Scores (){}

    public Scores(int score, String nick){
        this.score = score;
        this.nick = nick;
    }

    //Making JSON Object from class attributes
    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("nick", nick);
            obj.put("score", score);
        } catch (JSONException e) {
            System.out.println("DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }


}
