package com.cedro.memoriesoftravel.view.fragment;

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

import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.preseter.CountryPresenter;
import com.cedro.memoriesoftravel.view.adapters.CountryFragmentAdapter;

import java.util.ArrayList;
import java.util.List;


public class CountryFragment extends Fragment {
	
	ProgressBar pbar;
	ListView lsv_latest;
	CountryFragmentAdapter objAdapterHome;
	CountryPresenter presenter;
	public List<CountryModel> countryList;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(presenter == null)
			presenter = new CountryPresenter();


		View rootView = inflater.inflate(com.memoriesoftravel.R.layout.home_fragment, container, false);
		pbar=(ProgressBar)rootView.findViewById(com.memoriesoftravel.R.id.progressBar1);
		lsv_latest=(ListView)rootView.findViewById(com.memoriesoftravel.R.id.lsv_latest);
		countryList =new ArrayList<CountryModel>();

		presenter.onTakeView(this);
		presenter.loadCountry();

		
		lsv_latest.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				countryList.get(position);
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
