package com.cedro.memoriesoftravel.presenter;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.cedro.memoriesoftravel.dao.CountryDao;
import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.util.Constants;
import com.cedro.memoriesoftravel.util.Utils;
import com.cedro.memoriesoftravel.view.activity.CountryInfoActivity;
import com.cedro.memoriesoftravel.view.activity.MainActivity;
import com.cedro.memoriesoftravel.view.fragment.CountryFragment;
import com.cedro.memoriesoftravel.view.fragment.VisitedCountryFragment;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.memoriesoftravel.R;
import com.orm.SugarRecord;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by emerson on 06/10/16.
 */

public class VisitedCountryPresenter {
    VisitedCountryFragment view;
    List<CountryModel> countryList;
    boolean selected = false;
    public void onTakeView(VisitedCountryFragment view) {
        this.view = view;
    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }


    public void loadCountry() {
        CountryModel objItem;
        CountryModel country;


        view.pbar.setVisibility(View.VISIBLE);
        countryList = CountryDao.getVisitedCountries();
        view.countryList.clear();
        if(countryList != null && countryList.size() > 0){
            for(int i = 0; i < countryList.size(); i++){
                objItem = countryList.get(i);
                country = view.countryDao.addIfNotExists(objItem);
                view.countryList.add(country);
            }
            if(view.countryList != null){
                view.setAdapterToListview();
                view.pbar.setVisibility(View.INVISIBLE);
                view.lsv_latest.setVisibility(View.VISIBLE);
            }

        }else{
            view.lsv_latest.setVisibility(View.INVISIBLE);
            view.pbar.setVisibility(View.INVISIBLE);

        }

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
               Bundle extras = intent.getExtras();
               boolean s = extras.getBoolean("action");
                updateMenuSelect(s);

            }
        }, new IntentFilter("CHECK_VISITED"));

    }

    //se receber que existe um selecionado nesse momento podemos concluir que nao é necessario percorrer os outros paises para checar se estão marcados
    public void updateMenuSelect(boolean existeAoMenos1Selecionado){
        if(view.objAdapterHome.countryList == null)
            return;

        List<CountryModel> countryList = view.objAdapterHome.countryList;
        int qtdSelected = 0;

        if(existeAoMenos1Selecionado){
            qtdSelected = 1;
        }else {
            for(int i=0;i<countryList.size();i++){
                CountryModel country = countryList.get(i);
                if(country.isSelected()){
                    qtdSelected++;
                }
            }
        }


        if(qtdSelected > 0){
            if(view.excluirSelecionados == null)
                return;
            view.fab_menu.setVisibility(View.VISIBLE);
        }else {
            view.fab_menu.setVisibility(View.INVISIBLE);
        }
    }


    public void setupClickListener() {
        view.lsv_latest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.country_list_item_selectedCheckBox);
                if(!cb.isChecked()){
                    cb.setChecked(true);
                    CountryModel country = (CountryModel) cb.getTag();
                    country.setSelected(cb.isChecked());
                }
                return false;
            }
        });
        view.lsv_latest.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CheckBox cb = (CheckBox) view.findViewById(R.id.country_list_item_selectedCheckBox);

                if(cb.isChecked()){
                    cb.setChecked(false);
                    CountryModel country = (CountryModel) cb.getTag();
                    country.setSelected(cb.isChecked());
                }
                else {
                    // ao selecionar(TAP) deve abrir uma nova janela com os dados do pais
                    CountryModel country = countryList.get(position);
                    Intent i = new Intent(view.getContext(),CountryInfoActivity.class);
                    i.putExtra("country_id",country.getCountryId());
                    view.getContext().startActivity(i);
                }

            }
        });
    }

    public void setupCallbackFloating() {
        view.fab_menu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                view.rootView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        view.fab_menu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                view.rootView.setOnTouchListener(null);
            }
        });
        view.excluirSelecionados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.objAdapterHome.countryList == null)
                    return;

                List<CountryModel> countryList = view.objAdapterHome.countryList;
                for (int i = 0; i < countryList.size(); i++) {
                    CountryModel country = countryList.get(i);
                    if (country.isSelected()) {
                        country.removeVisit();
                        SugarRecord.save(country);
                    }
                }
                loadCountry();
                updateMenuSelect(false);
                sendBroadcastReload();

            }
        });
    }

    private void sendBroadcastReload() {
        Intent intnet = new Intent("RELOAD_VISITED");
        intnet.putExtra("action", "reload");
        view.getContext().sendBroadcast(intnet);

    }

}