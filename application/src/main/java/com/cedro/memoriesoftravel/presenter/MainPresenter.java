package com.cedro.memoriesoftravel.presenter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


import com.cedro.memoriesoftravel.R;
import com.cedro.memoriesoftravel.view.activity.MainActivity;
import com.cedro.memoriesoftravel.view.adapters.MainViewAdapter;

/**
 * Created by emerson on 06/10/16.
 */
public class MainPresenter implements MaterialTabListener {


    private Throwable error;

    private MainActivity view;

    private String[] tabs = {};

    Toolbar toolbar;

    MaterialTabHost tabHost;

    ViewPager pager;

    public MainPresenter() {


    }

    public void onTakeView(MainActivity view) {
        this.view = view;
    }

    public void initApp(){
        if(this.view != null){
            //define os titulos das tabs
            tabs = view.getResources().getString(R.string.tabs).split("\\s*,\\s*");
            //define o objeto toolbar da view
            toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            //define o titulo do aplicativo no toolbar
            toolbar.setTitle(view.getString(R.string.app_name));
            //cria o support action bar
            view.setSupportActionBar(toolbar);

            //define o objeto do tabHost
            tabHost = (MaterialTabHost) view.findViewById(R.id.tabHost);

            //define o viewPager
            pager = (ViewPager) view.findViewById(R.id.pager );

            // inicia o ViewPager
            initPager();
        }
    }

    private void initPager(){
        MainViewAdapter adapter = new MainViewAdapter(view.getSupportFragmentManager());
        pager.setAdapter(adapter);

        //for nas tabs adicionando ao menu
        for(String tab_name:tabs)
        {
            tabHost.addTab(
                    tabHost.newTab()
                            .setText(tab_name)
                            //.setIcon("")
                            .setTabListener(this)
            );
        }
    }

    private void publish() {
        if (view != null) {
            view.onItemsError(error);
        }
    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        pager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }
}
