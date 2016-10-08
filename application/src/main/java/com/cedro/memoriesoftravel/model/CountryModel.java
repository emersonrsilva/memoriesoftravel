package com.cedro.memoriesoftravel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import com.cedro.memoriesoftravel.util.Constants;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by emerson on 06/10/16.
 */

@Table
public class CountryModel {
    @SerializedName("id")
    @Expose
    private int countryid;

    private String iso;

    private String shortname;

    private String longname;

    private String callingCode;

    private int status;

    private String culture;

    private String visited = "false";

    private String dateVisite;


    private boolean selected = false;


    public void markVisited(int ano, int mes, int dia){
        mes++;
        this.dateVisite = dia+"/"+mes+"/"+ano;
        this.visited = "true";
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public void removeVisit() {
        this.visited = "false";
        this.selected = false;
    }

    public String getDateVisite() {
        return dateVisite;
    }

    public boolean isVisited() {
        return visited.equals("true");
    }

    public int getCountryId() {
        return countryid;
    }

    public void setId(int id) {
        this.countryid = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public String getCallingCode() {
        return callingCode;
    }

    public void setCallingCode(String callingCode) {
        this.callingCode = callingCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCulture() {
        return culture;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public String getImage() {
        return Constants.API_FLAGS.replaceAll("COUNTRY_ID",String.valueOf(countryid));
    }


}
