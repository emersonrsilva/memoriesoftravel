package com.cedro.memoriesoftravel.preseter;

import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.view.fragment.CountryFragment;

/**
 * Created by emerson on 06/10/16.
 */

public class CountryPresenter {
    CountryFragment view;
    public void onTakeView(CountryFragment view) {
        this.view = view;
    }


    public void loadCountry() {
        CountryModel objItem = new CountryModel();

        objItem.setId(01);
        objItem.setNome("Brazil222");
        objItem.setImage("http://sslapidev.mypush.com.br/world/countries/437/flag");//objJson.getString(Constant.LATEST_CHANNEL_IMAGE));
        view.countryList.add(objItem);

        view.setAdapterToListview();

    }
}
