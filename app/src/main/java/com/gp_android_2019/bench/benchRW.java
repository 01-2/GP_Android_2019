package com.gp_android_2019.bench;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class benchRW {
    private String target_path;
    private String type;
    private String direct;
    private String bs;
    private String io_size;
    private String num_jobs;
    private String runtime;

    private String home = "/data/data/com.gp_android_2019/";

    private String res = "";

    public benchRW (String path, String type, String direct, String bs, String io_size, String num_jobs, String runtime) {
        this.target_path = path;
        this.type = type;
        this.direct = direct;
        this.bs = bs;
        this.io_size = io_size;
        this.num_jobs = num_jobs;
        this.runtime = runtime;
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
                    "--bs=" + bs + " " + "--size=" + io_size + " " +
                    "--numjobs=" + num_jobs + " " + "--runtime=" + runtime;
            process = run.exec(cmd);
            System.out.print(cmd);
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
