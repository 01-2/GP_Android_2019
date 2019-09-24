package com.gp_android_2019.io;

import java.io.Serializable;

public class layer implements Serializable {
    public int pid;
    public int ext_r,ext_w, vfs_r, vfs_w, driver_r, driver_w, block_r, block_w = 0;

    layer(){
        pid = 0;
    }

    layer(layer l){
        pid = l.pid;
        ext_r = l.ext_r;
        ext_w = l.ext_w;
        vfs_r = l.vfs_r;
        vfs_w = l.vfs_w;
        driver_r = l.driver_r;
        driver_w = l.driver_w;
        block_r = l.block_r;
        block_w = l.block_w;
    }
    layer(Integer _pid){
        pid = _pid;
    }

    public void clear(){
        pid = 0;
        ext_r = ext_w = vfs_r = vfs_w = driver_r = driver_w = block_r = block_w = 0;

    }
}
