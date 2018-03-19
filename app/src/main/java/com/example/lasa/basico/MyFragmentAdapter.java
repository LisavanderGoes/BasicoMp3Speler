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
                infoFavoriteFragment tab1 = new infoFavoriteFragment();
                return tab1;
            case 1:
                infoAllSongsFragment tab2 = new infoAllSongsFragment();
                return tab2;
            case 2:
                infoPlaylistFragment tab3 = new infoPlaylistFragment();
                return tab3;
            case 3:
                infoAlbumFragment tab4 = new infoAlbumFragment();
                return tab4;
            case 4:
                infoGenreFragment tab5 = new infoGenreFragment();
                return tab5;
            case 5:
                infoArtistFragment tab6 = new infoArtistFragment();
                return tab6;
            case 6:
                infoBlackListFragment tab7 = new infoBlackListFragment();
                return tab7;
            case 7:
                infoMapFragment tab8 = new infoMapFragment();
                return tab8;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 8;
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
