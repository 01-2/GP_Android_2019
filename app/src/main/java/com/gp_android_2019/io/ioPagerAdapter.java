package com.gp_android_2019.io;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ioPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;

    public ioPagerAdapter(FragmentManager fm, int pageCount){
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                ioAppFragment appFragment = new ioAppFragment();
                return appFragment;
            case 1:
                ioFileFragment fileFragment = new ioFileFragment();
                return fileFragment;

                default:
                return null;
        }
    }
    @Override
    public int getCount(){
        return mPageCount;
    }

}
