package com.cedro.memoriesoftravel.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cedro.memoriesoftravel.view.fragment.CountryFragment;


/**
 * Created by emerson on 06/10/16.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public Fragment getItem(int num) {

        switch (num) {
            case 0:
                return new CountryFragment();
            case 1:
                return new CountryFragment();
            case 2:
                return new CountryFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}