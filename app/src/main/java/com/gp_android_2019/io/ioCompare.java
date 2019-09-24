package com.gp_android_2019.io;

import java.io.Serializable;

public class ioCompare implements Serializable {
    layer l1, l2;

    public ioCompare(layer _l1, layer _l2){
        super();
        l1 = _l1;
        l2 = _l2;
    }

    public layer getData1(){
        return l1;
    }
    public layer getData2(){
        return l2;
    }
    public void setData1(layer _l1){
        l1 = _l1;
    }
    public void setData2(layer _l2){
        l2 = _l2;
    }

}
