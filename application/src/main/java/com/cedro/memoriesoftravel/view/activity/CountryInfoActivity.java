package com.cedro.memoriesoftravel.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cedro.memoriesoftravel.presenter.CountryInfoPresenter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.memoriesoftravel.R;
import com.orm.SugarContext;


public class CountryInfoActivity extends ActionBarActivity {

	public boolean objects_init = false;
	CountryInfoPresenter presenter;
	public FloatingActionsMenu fabMenu;
	public Toolbar toolbar;
	public int countryId;
	public ImageView ivFlag;
	public TextView tLongName;
	public TextView tShortName;
	public TextView tCallCode;
	public FloatingActionButton btnEditar;
	public FloatingActionButton btnMarcarVisita;
	public FloatingActionButton btnRemoverVisita;
	public TextView txtMsg;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		SugarContext.init(getApplicationContext());
		Bundle extras = getIntent().getExtras();
		countryId = extras.getInt("country_id",0);
		if (countryId == 0) {
			finish();
		}
		setContentView(R.layout.about);
		if(presenter == null)
			presenter = new CountryInfoPresenter();
		presenter.onTakeView(this);
		presenter.initViewObjects();

		presenter.addFloatingMenuListener();
		presenter.loadCountryInfo();
		presenter.addFloatingMenuOptions();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{
		switch (menuItem.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				break;

			default:
				return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}



}
