package com.cedro.memoriesoftravel.dao;

import android.content.Context;
import android.database.SQLException;

import com.cedro.memoriesoftravel.model.CountryModel;
import com.orm.SugarRecord;

import java.util.List;


/**
 * Created by emerson on 07/10/16.
 */

public class CountryDao {
    public CountryModel addIfNotExists(final CountryModel mModel){
        // salvar no banco caso nao exista
        CountryModel tmp =  getCountryById(mModel.getCountryId());
        if(tmp == null) {
            SugarRecord.save(mModel);
            return mModel;
        }

        return tmp;
    }
    public static CountryModel getCountryById(int id){
        List<CountryModel> country = SugarRecord.find(CountryModel.class, "countryid = ?", String.valueOf(id));
        if(country.size() > 0)
            return country.get(0);
        return null;
    }

    public static List<CountryModel> getVisitedCountries(){
        List<CountryModel> country = SugarRecord.find(CountryModel.class, "visited = ?", String.valueOf(true));
        if(country.size() > 0)
            return country;
        return null;

    }


}
