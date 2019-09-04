package com.gp_android_2019.bench;

import java.util.ArrayList;

public class benchParameter {
    private String type;
    private String bs;
    private String io_size;
    private String num_jobs;
    private String runtime;
    private String direct;

    public benchParameter() { }

    public void setType(String type) { this.type = type; }
    public void setBs(String bs) { this.bs = bs; }
    public void setIo_size(String io_size) { this.io_size = io_size; }
    public void setNum_jobs(String num_jobs) { this.num_jobs = num_jobs; }
    public void setRuntime(String runtime) { this.runtime = runtime; }
    public void setDirect(int direct) {
        if (direct == 0) {
            this.direct = "0";
        }
        else if (direct == 1) {
            this.direct = "1";
        }
    }

    public String getType() { return type; }
    public String getBs() { return bs; }
    public String getIo_size() { return io_size; }
    public String getNum_jobs() { return num_jobs; }
    public String getRuntime() { return runtime; }
    public String getDirect() { return direct; }
}
