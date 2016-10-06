package com.cedro.memoriesoftravel.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cedro.memoriesoftravel.R;
import com.cedro.memoriesoftravel.presenter.MainPresenter;

import it.neokree.materialtabs.MaterialTabListener;

/**
 * Created by emerson on 06/10/16.
 */

public class MainActivity extends AppCompatActivity  {



    private static MainPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView listView = (ListView)findViewById(R.id.listView);
        //listView.setAdapter(new ArrayAdapter<>(this, R.layout.item));

        // Se o presenter não existir, deve ser instanciado
        if (presenter == null)
            presenter = new MainPresenter();

        //passa a view para o presenter
        presenter.onTakeView(this);
        presenter.initApp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //remove a view do presenter
        presenter.onTakeView(null);
        if (!isChangingConfigurations())
            presenter = null;
    }




    public void onItemsError(Throwable throwable) {
        //exibe um toast com o texto da exception
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }




}
