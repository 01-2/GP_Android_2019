package com.gp_android_2019.bench;

public class benchDB {
    private DBHelper dbHelper;
    private int numOfTrans;
    private boolean isEmpty = true;

    benchDB(DBHelper helper, int num) {
        dbHelper = helper;
        numOfTrans = num;
    }

    private void genWorkLoad_INSERT() {
        for (int i=0; i<numOfTrans; i++) {
            dbHelper.insert();
        }
        isEmpty = false;
    }
    private void genWorkLoad_UPDATE() {
        for (int i=0; i<numOfTrans; i++) {
            dbHelper.update(i);
        }
    }
    private void genWorkLoad_DELETE() {
        for (int i=0; i<numOfTrans; i++) {
            dbHelper.delete(i);
        }
    }

    public String benchInsert() {
        long start = System.currentTimeMillis();
        genWorkLoad_INSERT();
        long end = System.currentTimeMillis();

        return String.format("%.1f", (float)numOfTrans / ((float)(end - start)/1000));
    }

    public String benchUpdate() {
        if (isEmpty) {
            genWorkLoad_INSERT();
        }

        long start = System.currentTimeMillis();
        genWorkLoad_UPDATE();
        long end = System.currentTimeMillis();

        return String.format("%.1f", (float)numOfTrans / ((float)(end - start)/1000));
    }

    public String benchDelete() {
        if (isEmpty) {
            genWorkLoad_INSERT();
        }

        long start = System.currentTimeMillis();
        genWorkLoad_DELETE();
        long end = System.currentTimeMillis();

        return String.format("%.1f", (float)numOfTrans / ((float)(end - start)/1000));
    }
}
