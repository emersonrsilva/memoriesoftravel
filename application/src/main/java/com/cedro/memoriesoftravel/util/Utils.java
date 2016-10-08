package com.cedro.memoriesoftravel.util;

import com.cedro.memoriesoftravel.model.CountryModel;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

    public static ArrayList<CountryModel> countryAlfabetically(ArrayList<CountryModel> contriesList) {
        //ordena os paises alfabeticamente
        //fonte http://stackoverflow.com/questions/1814095/sorting-an-arraylist-of-contacts-based-on-name/37077669#37077669
        Collections.sort(contriesList, new Comparator<CountryModel>() {
            @Override
            public int compare(CountryModel country, CountryModel country2) {
                return country.getShortname().compareTo(country2.getShortname());
            }
        });
        return contriesList;

    }

}