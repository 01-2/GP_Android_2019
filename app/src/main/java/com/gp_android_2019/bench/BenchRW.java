package com.gp_android_2019.bench;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BenchRW {
    private String target_path = "tmp";
    private String type;
    private String direct;
    private String bs;
    private String io_tot_size;
    private String num_jobs;
    private String runtime;

    private String home = "/data/data/com.gp_android_2019/";

    private String res = "";

    public BenchRW (benchParameter bp) {
        this.type = bp.getType();
        this.bs = bp.getBs();
        this.io_tot_size = bp.getIo_size();
        this.num_jobs = bp.getNum_jobs();
        this.runtime = bp.getRuntime();
        this.direct = bp.getDirect();
    }

    public String getResult() {
        Runtime run = Runtime.getRuntime();

        try {
            String cmd;

            Process process;
            cmd = "fio " + " --output-format=json " +
                    "--directory=" + home + target_path + " " +
                    "--name=fio_test_file " +
                    "--rw=" + type + " " + "--direct=" + direct + " " +
                    "--bs=" + bs + " " + "--size=" + io_tot_size + " " +
                    "--numjobs=" + num_jobs + " " + "--runtime=" + runtime;
            System.out.println("cmd: " + cmd);
            process = run.exec(cmd);

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) {
                res += line;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }

        return res;
    }
}
