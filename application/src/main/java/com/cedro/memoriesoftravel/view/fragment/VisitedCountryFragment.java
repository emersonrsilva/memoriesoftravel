package com.cedro.memoriesoftravel.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cedro.memoriesoftravel.dao.CountryDao;
import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.presenter.VisitedCountryPresenter;
import com.cedro.memoriesoftravel.view.adapters.VisitedCountryFragmentAdapter;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.memoriesoftravel.R;

import java.util.ArrayList;
import java.util.List;


public class VisitedCountryFragment extends Fragment {
	
	public ListView lsv_latest;
	public VisitedCountryFragmentAdapter objAdapterHome;
	VisitedCountryPresenter presenter;
	public List<CountryModel> countryList;
	public CountryDao countryDao;
	public ProgressBar pbar;
	public FloatingActionButton excluirSelecionados;
	public FloatingActionsMenu fab_menu;
	public View rootView;
	private BroadcastReceiver broadCastReceiverVisited;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(presenter == null)
			presenter = new VisitedCountryPresenter();
		if(countryDao == null)
			countryDao = new CountryDao();


		rootView = inflater.inflate(com.memoriesoftravel.R.layout.country_fragment, container, false);
		pbar=(ProgressBar)rootView.findViewById(com.memoriesoftravel.R.id.progressBar1);
		lsv_latest=(ListView)rootView.findViewById(com.memoriesoftravel.R.id.lsv_latest);
		excluirSelecionados = (FloatingActionButton) rootView.findViewById(R.id.excluirSelecionados);
		fab_menu = (FloatingActionsMenu) rootView.findViewById(R.id.fab_menu);
		countryList =new ArrayList<CountryModel>();
		presenter.onTakeView(this);
		presenter.setupBroadcastReceiver();
		presenter.setupCallbackFloating();

		//carrega os paises na listview
		presenter.loadCountry();
		presenter.setupClickListener();





		return rootView;
	}



	  
	public void setAdapterToListview() {
		if (getActivity() != null) {
			objAdapterHome = new VisitedCountryFragmentAdapter(this, com.memoriesoftravel.R.layout.list_row,
					countryList) ;
			lsv_latest.setAdapter(objAdapterHome);
		}
	}
	
	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
}
