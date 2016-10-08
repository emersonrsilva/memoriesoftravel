package com.cedro.memoriesoftravel.view.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cedro.memoriesoftravel.view.fragment.CountryFragment;
import com.cedro.memoriesoftravel.view.fragment.HomeFragment;
import com.cedro.memoriesoftravel.view.fragment.VisitedCountryFragment;



/**
 * Created by emerson on 06/10/16.
 */

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private Activity activity;

    public MainViewPagerAdapter(FragmentManager fm, Activity act) {
        super(fm);
        this.activity = act;

    }

    public Fragment getItem(int num) {

        switch (num) {
            case 0:
                return new HomeFragment(activity);
            case 1:
                return new CountryFragment();
            case 2:
                return new VisitedCountryFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

}