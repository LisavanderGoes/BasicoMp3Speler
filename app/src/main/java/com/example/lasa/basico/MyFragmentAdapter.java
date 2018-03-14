package com.example.lasa.basico;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    public MyFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                infoAllSongsFragment tab1 = new infoAllSongsFragment();
                return tab1;
            case 1:
                infoPlaylistFragment tab2 = new infoPlaylistFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position){
        switch (position) {
            case 0:
                return "Songs";
            case 1:
                return "ObPlaylist";

        }
    return null;
    }
}
