package com.gp_android_2019.bench;

public class BenchDB {
    DBHelper dbHelper;
    int numOfTrans;
    boolean isEmpty = true;

    BenchDB(DBHelper helper, int num) {
        dbHelper = helper;
        numOfTrans = num;
    }

    void genWorkLoad_INSERT() {
        for (int i=0; i<numOfTrans; i++) {
            dbHelper.insert();
        }
        isEmpty = false;
    }
    void genWorkLoad_UPDATE() {
        for (int i=0; i<numOfTrans; i++) {
            dbHelper.update(i);
        }
    }
    void genWorkLoad_DELETE() {
        for (int i=0; i<numOfTrans; i++) {
            dbHelper.delete(i);
        }
    }

    String benchInsert() {
        long start = System.currentTimeMillis();
        genWorkLoad_INSERT();
        long end = System.currentTimeMillis();

        return (long)numOfTrans / ((end - start)/1000) + " trans/s";
    }

    String benchUpdate() {
        if (isEmpty) {
            genWorkLoad_INSERT();
        }

        long start = System.currentTimeMillis();
        genWorkLoad_UPDATE();
        long end = System.currentTimeMillis();

        return (long)numOfTrans / ((end - start)/1000) + " trans/s";
    }

    String benchDelete() {
        if (isEmpty) {
            genWorkLoad_INSERT();
        }

        long start = System.currentTimeMillis();
        genWorkLoad_DELETE();
        long end = System.currentTimeMillis();

        return (long)numOfTrans / ((end - start) / 1000) + " trans/s";
    }
}
