package com.cedro.memoriesoftravel.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;

import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.util.Constants;
import com.cedro.memoriesoftravel.util.Utils;
import com.cedro.memoriesoftravel.view.fragment.CountryFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by emerson on 06/10/16.
 */

public class CountryPresenter {
    CountryFragment view;
    public void onTakeView(CountryFragment view) {
        this.view = view;
    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    public void setupBroadcastReceiver() {

        view.getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadCountry();
            }
        }, new IntentFilter("RELOAD_VISITED"));

        view.getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadCountry();
            }
        }, new IntentFilter("CHECK_VISITED"));
    }


    public void loadCountry() {
        view.pbar.setVisibility(View.VISIBLE);
        Ion.with(view).load(Constants.API_COUNTRIES).asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                CountryModel objItem;
                CountryModel country;
                if(result != null && !result.equals("")){
                    ArrayList<CountryModel> countryList = (ArrayList<CountryModel>) fromJson(result,
                            new TypeToken<ArrayList<CountryModel>>() {}.getType());

                    countryList = Utils.countryAlfabetically(countryList);
                    view.countryList.clear();
                    for(int i = 0; i < countryList.size(); i++){
                        objItem = countryList.get(i);
                        country = view.countryDao.addIfNotExists(objItem);
                        view.countryList.add(country);

                    }

                }
                view.setAdapterToListview();
                view.pbar.setVisibility(View.INVISIBLE);

            }
        });
    }


}
