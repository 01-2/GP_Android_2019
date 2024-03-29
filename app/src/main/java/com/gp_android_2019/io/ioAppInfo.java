package com.gp_android_2019.io;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;

import java.text.Collator;
import java.util.Comparator;

public class ioAppInfo {
    public static interface AppFilter {
        public void init();
        public boolean filterApp(ApplicationInfo info);
    }

    public Drawable mIcon = null;
    public String mAppName = null;
    public String mAppPackge = null;
    public String mDataDir = null;
    public String mSrcDir = null;

    public static final AppFilter THIRD_PARTY_FILTER = new AppFilter() {
        public void init() {
        }

        @Override
        public boolean filterApp(ApplicationInfo info) {
            if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                return true;
            } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                return true;
            }
            return false;
        }
    };
    public static final Comparator<ioAppInfo> ALPHA_COMPARATOR = new Comparator<ioAppInfo>() {
        private final Collator sCollator = Collator.getInstance();
        @Override
        public int compare(ioAppInfo object1, ioAppInfo object2) {
            return sCollator.compare(object1.mAppName, object2.mAppName);
        }
    };
}
