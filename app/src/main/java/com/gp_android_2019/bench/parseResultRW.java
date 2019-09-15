package com.gp_android_2019.bench;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class parseResultRW {
    private String par;

    public parseResultRW(String result) {

        try {
            int id = Integer.valueOf(result.substring(0,1));
            String json = result.substring(1);

            JSONObject tot = new JSONObject(json);
            JSONArray jobs = tot.getJSONArray("jobs");

            JSONObject type = jobs.getJSONObject(0);
            JSONObject bw = new JSONObject();

            switch (id) {
                case 0:
                    bw = type.getJSONObject("read");
                    break;
                case 1:
                    bw = type.getJSONObject("write");
                    break;
                case 2:
                    bw = type.getJSONObject("read");
                    break;
                case 3:
                    bw = type.getJSONObject("write");
                    break;
                default:
                    break;
            }

            par = id + bw.getString("bw");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getPar() {
        return par;
    }
}
