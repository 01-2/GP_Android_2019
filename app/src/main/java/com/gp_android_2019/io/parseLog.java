package com.gp_android_2019.io;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class parseLog {
    ArrayList<String> log;
    ArrayList<String> read;
    ArrayList<String> write;


    public ArrayList<Integer> parse(Integer pid){
        String readString = "[VFS/READ] ";
        String writeString = "[VFS/WRITE] ";

        Integer sumRead = 0;
        Integer sumWrite = 0;

        ArrayList<Integer> result = new ArrayList<>(); // index 0 : sum of read
                                                       //       1 : sum of write

        read = new ArrayList<>();
        write = new ArrayList<>();

        for(String str : log){
            if(str.indexOf("-") == -1) continue; // pid not found

            int pos = str.indexOf("-");
            int space = str.indexOf(" ");

            if(pid != Integer.parseInt(str.substring(pos + 1,space))) continue; // pid not match
            else{
                if (str.indexOf(readString) != -1) { // [VFS/READ]
                    pos = str.indexOf(readString);
                    pos += readString.length();

                    read.add(str);
                    str = str.substring(pos, str.length());
                    sumRead += Integer.parseInt(str);
                }
                else if (str.indexOf((writeString)) != -1){ // [VFS/WRITE]
                    pos = str.indexOf(writeString);
                    pos += writeString.length();

                    write.add(str);
                    str = str.substring(pos, str.length()); // not found both format
                    sumWrite += Integer.parseInt(str);
                }
                else continue;
            }
        }

        result.add(sumRead);
        result.add(sumWrite);

        return result;
    }

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

    parseLog(){
        // log = suCommand("cat /data/data/com.termux/files/home/test.txt");
        log = suCommand("cat /sys/kernel/debug/tracing/trace");

        for(Iterator<String> it = log.iterator(); it.hasNext();){
            String str = it.next();
            if(str.startsWith("#")) it.remove();
        }
    }

    public void refreshLog(){
        log = suCommand("cat /sys/kernel/debug/tracing/trace");

        for(Iterator<String> it = log.iterator(); it.hasNext();){
            String str = it.next();
            if(str.startsWith("#")) it.remove();
        }
    }
}
