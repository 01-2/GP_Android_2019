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
    public Double appFragmentedPercentage;
    public Integer appFragmentedFileNum;
    public Integer defragMentedNum;

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
        Integer file_num = files.size();

        ArrayList<String> needDefrag = new ArrayList<>();

        for(String str : files){
            String tmp = new String(str);

            String num = new String();
            Integer cnt = str.length() - 1, result = 0;
            Integer check = 0;

            while(check < 2){
                if (str.charAt(cnt) == ' ')
                    check++;
                cnt --;
            }

            str = str.substring(0, cnt+1);

            while(str.charAt(cnt) != ' '){
                num += str.charAt(cnt);
                cnt --;
            }

            str = str.substring(0, cnt + 1);

            if (num != null) {
                try {
                    num = (new StringBuffer(num)).reverse().toString();
                    result = Integer.parseInt(num);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            if(result > 1){
                needDefrag.add(str);
            }
        }

        return needDefrag;
    }

    public void defragAllFiles(){
        String defrag = "/system/xbin/e4defrag " + appDataPath;
        String frag = "find " + appDataPath + " f -exec /system/xbin/filefrag {} \\;";

        defragMentedNum = appFragmentedFileNum;
        suCommand(defrag);

        ArrayList<String> files = suCommand(frag);
        appNeedToDefrag = getFragmentedFiles(files);
        appFragmentedFileNum = appNeedToDefrag.size();

        defragMentedNum -= appFragmentedFileNum;
        appFragmentedPercentage = Double.valueOf(appFragmentedFileNum) / Double.valueOf(appFileNum);
    }

    fragManage(String path){
        appDataPath = path;

        String frag = "find " + appDataPath + " f -exec /system/xbin/filefrag {} \\;";
        ArrayList<String> files = suCommand(frag);

        parseLog p = new parseLog();
        p.parseAll(3569);

        appFileNum = files.size();
        appNeedToDefrag = getFragmentedFiles(files);
        appFragmentedFileNum = appNeedToDefrag.size();

        appFragmentedPercentage = Double.valueOf(appFragmentedFileNum) / Double.valueOf(appFileNum);
    }

}
