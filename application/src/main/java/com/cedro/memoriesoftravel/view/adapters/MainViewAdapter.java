package com.cedro.memoriesoftravel.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;

import com.cedro.memoriesoftravel.view.frangments.HomeFragment;

import java.util.List;

/**
 * Created by emerson on 06/10/16.
 */

public class MainViewAdapter  extends FragmentStatePagerAdapter {
    List<Fragment> listaFragmentsMain;
    public MainViewAdapter(FragmentManager fm) {
        super(fm);
        listaFragmentsMain.add(new HomeFragment());
        //listaFragmentsMain.add(new ContryFragment());
        //listaFragmentsMain.add(new VisitedFragment());

    }

    public Fragment getItem(int num) {

        switch (num) {

            default:
               listaFragmentsMain.get(num);
        }
        return null;
    }

    @Override
    public int getCount() {
        if(listaFragmentsMain != null)
            return listaFragmentsMain.size();
        return 0;
    }

}
