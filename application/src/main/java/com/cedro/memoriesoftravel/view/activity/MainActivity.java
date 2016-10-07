package com.cedro.memoriesoftravel.view.activity;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabListener;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.cedro.memoriesoftravel.preseter.MainPresenter;
import com.memoriesoftravel.R;

/**
 * Created by emerson on 06/10/16.
 */

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

	public ViewPager pager;

	MainPresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		presenter = new MainPresenter();
		presenter.onTakeView(this);
		presenter.init();


	}



	@Override
	public void onTabSelected(MaterialTab tab) {
		// TODO Auto-generated method stub
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(MaterialTab tab) {
		// TODO Auto-generated method stub

	}





}
