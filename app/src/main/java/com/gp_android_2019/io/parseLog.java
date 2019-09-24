package com.gp_android_2019.io;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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



    public layer parseAll(Integer pid){
        String reg = ".*\\:.*\\: ";
        String reg2 = ".*-";
        Pattern p = Pattern.compile(reg);
        Pattern p2 = Pattern.compile(reg2);
        layer ret = new layer(pid);

        for(String str: log){
        //    if(str.indexOf("-") == -1) continue; // pid not found


          //  str = str.substring(4, str.length());
          //  int pos = str.indexOf("-");
           // int space = str.indexOf(" ");;

            Matcher m = p2.matcher(str);
            if(!m.find())
                continue;

            int pos = m.group().length();
            str = str.substring(pos, str.length());
            int space = str.indexOf(" ");;

            if(pid != Integer.parseInt(str.substring(0, space))) continue;

            m = p.matcher(str);
            if(m.find()){
                pos = m.group().length();
                str = str.substring(pos, str.length());

                pos = str.indexOf("/");
                String mode = str.substring(1, pos);

                pos += 1;

                String rw = str.substring(pos, pos + 1);

                pos = str.indexOf(" ");
                int amount = Integer.parseInt(str.substring(pos + 1, str.length()));

                if(mode.equals("EXT4") && rw.equals("R"))
                    ret.ext_r += amount;
                else if (mode.equals("EXT4") && rw.equals("W"))
                    ret.ext_w += amount;
                else if (mode.equals("VFS") && rw.equals("R"))
                    ret.vfs_r += amount;
                else if (mode.equals("VFS") && rw.equals("W"))
                    ret.vfs_w += amount;
                else if (mode.equals("BLOCK") && rw.equals("R"))
                    ret.block_r += amount;
                else if (mode.equals("BLOCK") && rw.equals("W"))
                    ret.block_w += amount;
                else if (mode.equals("DRIVER") && rw.equals("R"))
                    ret.driver_r += amount;
                else if (mode.equals("DRIVer") && rw.equals("W"))
                    ret.driver_w += amount;

            }

        }

        return ret;
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
