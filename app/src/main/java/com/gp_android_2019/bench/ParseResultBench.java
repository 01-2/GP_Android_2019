package com.gp_android_2019.bench;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class ParseResultBench {
    private ArrayList<String> par = new ArrayList<String>();;

    public ParseResultBench(ArrayList<String> results) {

        for (int i=0; i < results.size(); i++) {
            try {
                String tmp = results.get(i);
                int id = Integer.valueOf(tmp.substring(0,1));
                String json = tmp.substring(1);

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

                par.add(id + bw.getString("bw") + " KB/s");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getPar() {
        return par;
    }
}
