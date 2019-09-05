package com.gp_android_2019.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class fragManage {
    public String appDataPath;
    public Integer appFileNum;
    public ArrayList<String> appNeedToDefrag;
    public Integer appFragmentedFileNum;

    public ArrayList<String> suCommand(String cmd){
        Process p;
        try{
            p = Runtime.getRuntime().exec(new String[]{"su", "-c", cmd});
            DataOutputStream os = new DataOutputStream(p.getOutputStream());
            BufferedReader bf = new BufferedReader(new InputStreamReader(p.getInputStream()));

            ArrayList<String> ret = new ArrayList<>();
            String test;

            while((test = bf.readLine()) != null){
                ret.add(test);
            }

            os.flush();
            return ret;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<String> getFragmentedFiles(ArrayList<String> files){
        ArrayList<String> needDefrag = new ArrayList<>();

        for(String str : files){
            str = str.substring(0, str.length() - 13);

            String num = new String();
            Integer cnt = str.length() - 1, result = 0;

            while(str.charAt(cnt) != ' '){
                num += str.charAt(cnt);
                cnt --;
            }

            cnt --;
            str = str.substring(0, str.length() - cnt);

            num = (new StringBuffer(num)).reverse().toString();
            result = Integer.parseInt(str);

            if(result > 1){
                needDefrag.add(str);
            }
        }

        return files;
    }


    fragManage(String path){
        appDataPath = path;

        String frag = "find " + appDataPath + " f -exec /data/data/com.termux/files/usr/bin/filefrag {} \\;";

        ArrayList<String> ret = suCommand(frag);
        appFileNum = ret.size();



    }

}
