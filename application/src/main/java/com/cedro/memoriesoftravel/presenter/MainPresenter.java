package com.cedro.memoriesoftravel.presenter;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.cedro.memoriesoftravel.view.activity.LoginActivity;
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

        view.getSupportActionBar().setDisplayHomeAsUpEnabled(true);//



        tabHost = (MaterialTabHost) view.findViewById(R.id.tabHost);
        view.pager = (ViewPager) view.findViewById(R.id.pager );

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(view.getSupportFragmentManager(),view);
        view.pager.setAdapter(adapter);

        view.pager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
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

