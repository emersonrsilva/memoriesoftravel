package com.cedro.memoriesoftravel.preseter;


import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.cedro.memoriesoftravel.view.activity.MainActivity;
import com.cedro.memoriesoftravel.view.adapters.MainViewPagerAdapter;
import com.memoriesoftravel.R;

import it.neokree.materialtabs.MaterialTabHost;


/**
 * Created by emerson on 06/10/16.
 */
public class MainPresenter {

    private MainActivity view;
    private String[] tabs = {};


    Toolbar toolbar;
    MaterialTabHost tabHost;


    public void onTakeView(MainActivity view) {
        this.view = view;
    }

    public void init(){
        tabs = view.getResources().getString(R.string.tabs).split("\\s*,\\s*");
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        toolbar.setTitle(view.getString(R.string.app_name));
        view.setSupportActionBar(toolbar);




        tabHost = (MaterialTabHost) view.findViewById(R.id.tabHost);
        view.pager = (ViewPager) view.findViewById(R.id.pager );

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(view.getSupportFragmentManager());
        view.pager.setAdapter(adapter);

        view.pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // when user do a swipe the selected tab change
                tabHost.setSelectedNavigationItem(position);

            }
        });

        for(String tab_name:tabs)
        {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(tab_name)
                            .setTabListener(view)
            );
        }
    }

}
