package com.gp_android_2019.bench;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class benchPagerAdapter extends FragmentStatePagerAdapter {
    int num_tabs;

    public benchPagerAdapter(FragmentManager fm, int num_tabs) {
        super(fm);
        this.num_tabs = num_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                benchTypeFragment btf = new benchTypeFragment();
                return btf;
            case 1:
                benchParamFragment bpf = new benchParamFragment();
                return bpf;
            default:
                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return num_tabs;
    }
}
