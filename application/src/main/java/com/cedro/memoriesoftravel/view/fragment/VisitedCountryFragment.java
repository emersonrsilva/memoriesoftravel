package com.cedro.memoriesoftravel.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cedro.memoriesoftravel.dao.CountryDao;
import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.presenter.CountryPresenter;
import com.cedro.memoriesoftravel.view.activity.CountryInfoActivity;
import com.cedro.memoriesoftravel.view.adapters.CountryFragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class VisitedCountryFragment extends Fragment {
	
	ListView lsv_latest;
	CountryFragmentAdapter objAdapterHome;
	CountryPresenter presenter;
	public List<CountryModel> countryList;
	public CountryDao countryDao;
	public ProgressBar pbar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(presenter == null)
			presenter = new CountryPresenter();
		if(countryDao == null)
			countryDao = new CountryDao();


		View rootView = inflater.inflate(com.memoriesoftravel.R.layout.home_fragment, container, false);
		pbar=(ProgressBar)rootView.findViewById(com.memoriesoftravel.R.id.progressBar1);
		lsv_latest=(ListView)rootView.findViewById(com.memoriesoftravel.R.id.lsv_latest);
		countryList =new ArrayList<CountryModel>();
		presenter.onTakeView(this);

		//carrega os paises na listview
		presenter.loadCountry();

		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ao selecionar(TAP) deve abrir uma nova janela com os dados do pais
				CountryModel country = countryList.get(position);
				Intent i = new Intent(getContext(),CountryInfoActivity.class);
				i.putExtra("country_id",country.getCountryId());
				startActivity(i);
			}
		});


		return rootView;
	}

	  
	public void setAdapterToListview() {
		objAdapterHome = new CountryFragmentAdapter(this, com.memoriesoftravel.R.layout.list_row,
				countryList) ;
		lsv_latest.setAdapter(objAdapterHome);
	}
	
	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
	}
}
