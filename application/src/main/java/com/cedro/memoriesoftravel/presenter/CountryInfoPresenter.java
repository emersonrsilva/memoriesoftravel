package com.cedro.memoriesoftravel.presenter;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cedro.memoriesoftravel.dao.CountryDao;
import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.util.DialogManager;
import com.cedro.memoriesoftravel.util.ImageLoader;
import com.cedro.memoriesoftravel.view.activity.CountryInfoActivity;
import com.cedro.memoriesoftravel.view.fragment.CountryFragment;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.memoriesoftravel.R;
import com.orm.SugarDb;
import com.orm.SugarRecord;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by emerson on 07/10/16.
 */

public class CountryInfoPresenter {

    CountryInfoActivity view;
    CountryModel country;

    public void onTakeView(CountryInfoActivity view) {
        this.view = view;
    }

    public void loadCountryInfo(){
        country = CountryDao.getCountryById(view.countryId);
        if(country == null)
            return;
        if(!view.objects_init){
            initViewObjects();
        }
        view.setSupportActionBar(view.toolbar);
        view.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view.getSupportActionBar().setDisplayShowHomeEnabled(true);

        view.toolbar.setTitle(country.getLongname());

        new ImageLoader(view.getApplicationContext()).exibirImagem(country.getImage(),view.ivFlag);
        view.setSupportActionBar(view.toolbar);
        view.tLongName.setText(country.getLongname());
        view.tShortName.setText(country.getShortname());
        view.tCallCode.setText(country.getCallingCode());

        if(country.isVisited()){
            view.txtMsg.setText("Voce visitou este pais! \nEm: "+country.getDateVisite());

        }
    }

    public void addFloatingMenuListener() {
        final FrameLayout frameLayout = (FrameLayout) view.findViewById(R.id.activity_layout);

        view.fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        view.fabMenu.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
            }
        });

        view.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.fabMenu.collapse();

                LayoutInflater layoutInflater = LayoutInflater.from(view);
                View promptView = layoutInflater.inflate(R.layout.dialog_new_name, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view);
                alertDialogBuilder.setView(promptView);

                final EditText name = (EditText) promptView.findViewById(R.id.countryNewName);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String newName = name.getText().toString();
                                CountryDao.editCountryName(country.getCountryId(),newName);
                                updateInfoInViews();
                                dialog.dismiss();

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                AlertDialog alert = alertDialogBuilder.create();
                alert.show();

            }
        });

        view.btnMarcarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.fabMenu.collapse();
                openMarkVisitDialog();
            }
        });
        view.btnRemoverVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.fabMenu.collapse();
                removeVisit();
            }
        });
    }

    public void initViewObjects() {
        view.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        view.fabMenu = (FloatingActionsMenu) view.findViewById(R.id.fab_menu);
        view.ivFlag = (ImageView) view.findViewById(R.id.imageViewFlag);
        view.tLongName = (TextView) view.findViewById(R.id.txtLongName);
        view.tShortName = (TextView) view.findViewById(R.id.txtShortName);
        view.tCallCode = (TextView) view.findViewById(R.id.txtCallCode);
        view.btnMarcarVisita = (FloatingActionButton) view.findViewById(R.id.btnMarcarVisita);
        view.btnRemoverVisita = (FloatingActionButton) view.findViewById(R.id.btnRemoverVisita);
        view.btnEditar = (FloatingActionButton) view.findViewById(R.id.btnEditar);
        view.txtMsg= (TextView) view.findViewById(R.id.txtMsg);

    }

    public void addFloatingMenuOptions() {
        view.btnMarcarVisita.setVisibility(View.INVISIBLE);
        view.btnRemoverVisita.setVisibility(View.INVISIBLE);
        if(country.isVisited()){
            view.btnRemoverVisita.setVisibility(View.VISIBLE);
        }else {
            view.btnMarcarVisita.setVisibility(View.VISIBLE);
        }

    }

    public void openMarkVisitDialog(){
        DialogManager.showCalendarDialog(view,new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker viewDate, int ano, int mes, int dia) {
                CountryDao.markCountryVisited(country.getCountryId(),ano, mes,  dia);
                updateInfoInViews();
                DialogManager.showToast(view,viewDate.getResources().getString(R.string.txt_country_added));

            }

        });
    }

    private void removeVisit() {
        CountryDao.removeVisit(country);
        updateInfoInViews();
    }

    private void sendBroadcastReload(){
       Intent intnet = new Intent("RELOAD_VISITED");
        intnet.putExtra("action","reload");
       view.sendBroadcast(intnet);

    }

    private void updateInfoInViews(){
        sendBroadcastReload();
        loadCountryInfo();
        addFloatingMenuOptions();

    }
}
