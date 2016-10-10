package com.cedro.memoriesoftravel.dao;

import android.content.Context;
import android.database.SQLException;

import com.cedro.memoriesoftravel.model.CountryModel;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by emerson on 07/10/16.
 */

public class CountryDao {

    public CountryModel add(CountryModel model){
        SugarRecord.save(model);
        return model;
    }

    public CountryModel addIfNotExists(final CountryModel mModel){
        // salvar no banco caso nao exista
        CountryModel tmp =  getCountryById(mModel.getCountryId());
        if(tmp == null) {
            return add(mModel);
        }
        return tmp;
    }

    public static void editCountryName(int id, String newName){
        CountryModel country = getCountryById(id);

        SugarRecord.executeQuery("UPDATE COUNTRY_MODEL SET longname = ?, shortname = ? WHERE countryid = ?", new String[]{
                newName,
                newName,
                String.valueOf(country.getCountryId())
        });

    }

    public static void markCountryVisited(int id, int ano, int mes, int dia){
        CountryModel country = getCountryById(id);
        SugarRecord.executeQuery("UPDATE COUNTRY_MODEL SET datevisite = ?, visited = ? WHERE countryid = ?", new String[]{
                dia+"/"+mes+"/"+ano,
                "true",
                String.valueOf(country.getCountryId())
        });
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

    public static ArrayList<CountryModel> getAllCountries(){
        List<CountryModel> country = SugarRecord.listAll(CountryModel.class);
        if(country.size() > 0)
            return new ArrayList<CountryModel>(country);
        return null;

    }


    public static void removeVisit(CountryModel country) {
        SugarRecord.executeQuery("UPDATE COUNTRY_MODEL SET visited = ?, selected = ? WHERE countryid = ?", new String[]{"false","false",String.valueOf(country.getCountryId())});
       // country.removeVisit();
       // SugarRecord.save(country);
    }
}
